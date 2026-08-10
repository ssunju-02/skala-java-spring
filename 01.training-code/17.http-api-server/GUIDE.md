# 샘플로 Spring 구조 이해하기

Spring Framework 없이, 순수 Java의 **Annotation + Reflection + Socket** 기능만으로
`@Controller`, `@PathVariable`, `@GetMapping`, `@PostMapping`이 내부적으로 어떻게 동작하는지
아주 단순화해서 흉내 낸 미니 HTTP 서버입니다.

## 구조

```
sk/skala/com/httpserver/
├── Main.java                 서버 시작점 (컨트롤러 등록 + 서버 구동)
├── HttpWebServer.java         Socket으로 요청 수신, Reflection으로 라우팅/메소드 호출
├── HttpWebClient.java         Socket으로 raw HTTP 요청을 보내보는 테스트 클라이언트
├── annotation/
│   ├── Controller.java        클래스 레벨: 컨트롤러로 등록할 클래스 표시
│   ├── GetMapping.java         메소드 레벨: GET 요청과 경로 매핑
│   ├── PostMapping.java        메소드 레벨: POST 요청과 경로 매핑
│   └── PathVariable.java       파라미터 레벨: 경로의 {id} 값을 주입
├── controller/
│   └── UserController.java    @Controller 예제 (Spring의 @RestController 역할)
├── domain/
│   └── User.java               데이터 모델
└── util/
    └── JsonUtil.java           Jackson 없이 Reflection만으로 만든 초간단 JSON 변환기
```

## 동작 원리 (Spring MVC와 비교)

1. **컨트롤러 등록 (컴포넌트 스캔 흉내)**
   `Main`에서 `server.registerController(UserController.class)`를 호출하면,
   `HttpWebServer`가 Reflection으로 클래스에 `@Controller`가 있는지 확인하고,
   `newInstance()`로 인스턴스를 생성합니다. Spring이 `@ComponentScan`으로
   빈을 찾아 등록하는 것과 같은 역할입니다.

2. **라우트 등록 (`@GetMapping`/`@PostMapping` 스캔)**
   클래스의 모든 메소드를 순회하며 `@GetMapping`/`@PostMapping`이 붙어 있으면,
   HTTP method + 경로 패턴(`/users/{id}` → 정규식 `^/users/([^/]+)$`)을
   Map처럼 리스트에 저장합니다. Spring의 `RequestMappingHandlerMapping`과 같은 역할입니다.

3. **요청 수신 (Socket)**
   `ServerSocket.accept()`로 클라이언트 연결을 받고, `BufferedReader`로
   HTTP 요청 라인(`GET /users/1 HTTP/1.1`)과 헤더, body를 직접 텍스트로 파싱합니다.
   Tomcat이 내부적으로 하는 일을 아주 단순하게 재현한 것입니다.

4. **라우팅 및 호출 (`DispatcherServlet` 흉내)**
   들어온 method + path와 일치하는 라우트를 찾고, 파라미터에 `@PathVariable`이 있으면
   경로에서 뽑은 값을 넣고, 없으면 body를 JSON으로 파싱해서 넣은 뒤
   `Method.invoke()`로 실제 컨트롤러 메소드를 호출합니다.

5. **응답 (JSON 직렬화)**
   메소드의 리턴값을 `JsonUtil.toJson()`으로 직렬화해서 HTTP 응답으로 내려줍니다.

## 실행 방법

### 1) 서버 실행 (`01.training-code/17.http-api-server` 디렉토리에서)

```bash
javac -d out $(find sk -name "*.java") && echo BUILD_OK
java -cp out sk.skala.com.httpserver.Main
```

정상적으로 뜨면 아래처럼 라우트 등록 로그가 보입니다.

```
[컨트롤러 등록] UserController
  GET   /users -> getUsers()
  GET   /users/{id} -> getUser(경로 변수)
  POST  /users -> createUser()

HttpWebServer started on port 8080
등록된 라우트 수: 3
========================================
```

### 2) Client 실행 (다른 터미널에서)

```bash
java -cp out sk.skala.com.httpserver.HttpWebClient /users
java -cp out sk.skala.com.httpserver.HttpWebClient /users/1
```

### 3) 브라우저 / curl로 호출해보기

```
http://localhost:8080/users
http://localhost:8080/users/1
http://localhost:8080/users/2
http://localhost:8080/notfound   (404 확인용)
```

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"skala","email":"skala@example.com"}'
```
