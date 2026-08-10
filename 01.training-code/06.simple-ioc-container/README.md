# Simple IoC Container

Spring의 IoC 컨테이너가 내부적으로 어떻게 동작하는지, 순수 Java Reflection만으로 재현한 예제입니다.

- `annotation/Service.java` : `@Retention(RUNTIME)` 커스텀 `@Service` 어노테이션
- `container/SimpleIocContainer.java` : `register()`로 Bean 후보를 등록하고, `getBean()` 호출 시점에
  생성자 파라미터 타입을 재귀적으로 분석해서 의존성을 먼저 만든 뒤 주입하고, 싱글톤으로 캐시한다.
- `repository/UserRepository.java`, `service/UserService.java`, `service/TestService.java` : 예제 Bean들

## 실행 방법

```bash
javac -d bin $(find src -name "*.java")
java -cp bin com.sk.skala.ioc.Main
```

## 확인 포인트

- 개발자가 `new UserService(...)`를 직접 호출하지 않아도, 컨테이너가 생성자 시그니처만 보고
  `UserRepository`를 먼저 만들어 주입한다 (제어의 역전, IoC).
- `TestService` → `UserService` → `UserRepository` 순으로 의존성이 재귀적으로 해결된다.
- 같은 타입을 다시 요청해도 동일한 인스턴스가 반환된다 (싱글톤).
