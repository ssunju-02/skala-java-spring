import java.util.List;

public class BoundedBox {

    // Upper Bound Wildcard 메서드: Number 하위 타입 리스트의 합계를 반환 (읽기 전용)
    public static double sumBox(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        // 컴파일 에러: upper bound wildcard 리스트에는 추가 불가
        // list.add(10);
        return sum;
    }

    // Lower Bound Wildcard 메서드: Integer 및 그 상위 타입(Number, Object)만 허용 (쓰기 전용)
    public static void addBox(List<? super Integer> list, int value) {
        list.add(value);
        // 컴파일 에러: lower bound wildcard는 Object로만 꺼낼 수 있음
        // Integer first = list.get(0);
    }
}
