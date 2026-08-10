# DI 이해하기 (before / after)

- `before/` : 의존성 주입 전 — `Coffee`가 `kind` 문자열을 보고 직접 `new Hot()`, `new Ice()`, `new ThinIce()`를 생성한다.
- `after/` : 의존성 주입 후 — `Coffee`는 생성자로 `Ame` 구현체를 주입받기만 하고, 어떤 구현체인지는 알지 못한다.

## 실행 방법

```bash
cd before   # 또는 after
javac -d bin *.java
java -cp bin Main
```

## ThinIce 추가 실험 결과

- `before/Coffee.java` : `ThinIce`를 추가하려면 `coffeeType()`의 `if/else` 분기에 새 케이스를 넣어야 한다 → **기존 클래스를 수정**해야 함 (OCP 위반).
- `after/Coffee.java` : `ThinIce implements Ame`만 만들고 `new Coffee(new ThinIce())`로 호출하면 끝 → **Coffee.java는 한 줄도 수정하지 않음**.

이것이 DI(의존성 주입)가 결합도를 낮추고 확장에 유연해지는 이유입니다.
