import java.util.function.*;

public class LambdaExternalVariable {
    public static void main(String[] args) {
        int base = 10 + 5; // 단 한 번만 값이 대입되어야 effectively final

        Function<Integer, Integer> addBase = (x) -> x + base;
        System.out.println(addBase.apply(5)); // 20

        // 외부 변수는 람다식 내부에서 final 또는 effectively final이어야 함
        // 아래 줄의 주석을 해제하면 base가 두 번 대입되어 effectively final이 깨지므로 컴파일 오류 발생
        // base = 20;
    }
}
