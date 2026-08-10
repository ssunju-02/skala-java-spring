package sk.skala.com.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// Socket을 직접 열어서 raw HTTP 요청을 보내보는 테스트용 클라이언트
public class HttpWebClient {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8080;
        String path = args.length > 0 ? args[0] : "/users";

        try (Socket socket = new Socket(host, port)) {
            OutputStream out = socket.getOutputStream();
            String request = "GET " + path + " HTTP/1.1\r\n" +
                    "Host: " + host + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n";
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            System.out.println("=== " + path + " 응답 ===");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
