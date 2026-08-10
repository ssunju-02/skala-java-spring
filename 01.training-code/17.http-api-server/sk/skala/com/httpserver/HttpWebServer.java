package sk.skala.com.httpserver;

import sk.skala.com.httpserver.annotation.Controller;
import sk.skala.com.httpserver.annotation.GetMapping;
import sk.skala.com.httpserver.annotation.PathVariable;
import sk.skala.com.httpserver.annotation.PostMapping;
import sk.skala.com.httpserver.util.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpWebServer {

    // 등록된 라우트 하나를 표현: HTTP method + 경로 패턴 + 실행할 컨트롤러 인스턴스/메소드
    private record Route(String httpMethod, Pattern pathPattern, Object controllerInstance, Method method) {
    }

    private final List<Route> routes = new ArrayList<>();
    private final int port;

    public HttpWebServer(int port) {
        this.port = port;
    }

    // @Controller가 붙은 클래스를 Reflection으로 스캔해서, @GetMapping/@PostMapping 메소드를 라우트로 등록
    public void registerController(Class<?> controllerClass) {
        if (!controllerClass.isAnnotationPresent(Controller.class)) {
            throw new IllegalArgumentException(controllerClass.getName() + "에는 @Controller가 없습니다.");
        }
        try {
            Object instance = controllerClass.getDeclaredConstructor().newInstance();
            System.out.println("[컨트롤러 등록] " + controllerClass.getSimpleName());
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(GetMapping.class)) {
                    GetMapping mapping = method.getAnnotation(GetMapping.class);
                    addRoute("GET", mapping.value(), instance, method);
                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    PostMapping mapping = method.getAnnotation(PostMapping.class);
                    addRoute("POST", mapping.value(), instance, method);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("컨트롤러 등록 실패: " + controllerClass.getName(), e);
        }
    }

    // "/users/{id}" 같은 경로 패턴을 정규식으로 변환해서 등록
    private void addRoute(String httpMethod, String pathPattern, Object instance, Method method) {
        StringBuilder regex = new StringBuilder("^");
        boolean hasPathVariable = false;
        for (String segment : pathPattern.split("/")) {
            if (segment.isEmpty()) {
                continue;
            }
            if (segment.startsWith("{") && segment.endsWith("}")) {
                regex.append("/([^/]+)");
                hasPathVariable = true;
            } else {
                regex.append("/").append(Pattern.quote(segment));
            }
        }
        regex.append("$");
        routes.add(new Route(httpMethod, Pattern.compile(regex.toString()), instance, method));

        String display = hasPathVariable ? method.getName() + "(경로 변수)" : method.getName() + "()";
        System.out.printf("  %-5s %s -> %s%n", httpMethod, pathPattern, display);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println();
            System.out.println("HttpWebServer started on port " + port);
            System.out.println("등록된 라우트 수: " + routes.size());
            System.out.println("========================================");
            while (true) {
                Socket client = serverSocket.accept();
                handleClient(client);
            }
        }
    }

    // 소켓으로 들어온 raw HTTP 요청을 직접 파싱하고, 매칭되는 컨트롤러 메소드를 Reflection으로 호출
    private void handleClient(Socket client) {
        try (client;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
             OutputStream out = client.getOutputStream()) {

            String requestLine = reader.readLine();
            if (requestLine == null || requestLine.isBlank()) {
                return;
            }
            String[] parts = requestLine.split(" ");
            String httpMethod = parts[0];
            String fullPath = parts[1];
            String path = fullPath.contains("?") ? fullPath.substring(0, fullPath.indexOf('?')) : fullPath;

            int contentLength = 0;
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.toLowerCase().startsWith("content-length:")) {
                    contentLength = Integer.parseInt(line.split(":", 2)[1].trim());
                }
            }

            String body = "";
            if (contentLength > 0) {
                char[] buf = new char[contentLength];
                reader.read(buf, 0, contentLength);
                body = new String(buf);
            }

            Route matched = null;
            Matcher matcher = null;
            for (Route route : routes) {
                if (!route.httpMethod().equals(httpMethod)) {
                    continue;
                }
                Matcher m = route.pathPattern().matcher(path);
                if (m.matches()) {
                    matched = route;
                    matcher = m;
                    break;
                }
            }

            if (matched == null) {
                writeResponse(out, 404, "{\"error\":\"Not Found\",\"path\":\"" + path + "\"}");
                return;
            }

            try {
                Object result = invoke(matched, matcher, body);
                writeResponse(out, 200, JsonUtil.toJson(result));
            } catch (Exception e) {
                writeResponse(out, 500, "{\"error\":\"Internal Server Error\",\"message\":\"" + e.getMessage() + "\"}");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 메소드 파라미터에 @PathVariable이 있으면 경로에서 값을 꺼내고, 없으면 Request Body(JSON)로 간주해서 역직렬화
    private Object invoke(Route route, Matcher matcher, String body) throws Exception {
        Method method = route.method();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        int groupIndex = 1;
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            if (param.isAnnotationPresent(PathVariable.class)) {
                args[i] = convert(matcher.group(groupIndex++), param.getType());
            } else {
                args[i] = JsonUtil.fromJson(body, param.getType());
            }
        }
        method.setAccessible(true);
        return method.invoke(route.controllerInstance(), args);
    }

    private Object convert(String value, Class<?> type) {
        if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        }
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        }
        return value;
    }

    private void writeResponse(OutputStream out, int statusCode, String body) throws IOException {
        String statusText = switch (statusCode) {
            case 200 -> "OK";
            case 404 -> "Not Found";
            default -> "Internal Server Error";
        };
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        String header = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                "Content-Type: application/json; charset=UTF-8\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(bodyBytes);
        out.flush();
    }
}
