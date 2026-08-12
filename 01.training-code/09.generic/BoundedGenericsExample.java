import java.util.ArrayList;
import java.util.List;

public class BoundedGenericsExample {
    public static void main(String[] args) {
        // ---- Upper Bound Wildcard: sumBox(List<? extends Number>) ----
        List<Integer> intList = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Double> dblList = new ArrayList<>(List.of(1.1, 2.2, 3.3));
        System.out.println("Integer 리스트 합계: " + BoundedBox.sumBox(intList));
        System.out.println("Double  리스트 합계: " + BoundedBox.sumBox(dblList));

        // ---- Lower Bound Wildcard: addBox(List<? super Integer>, int) ----
        List<Number> numberList = new ArrayList<>(); // Number는 Integer의 상위 타입 -> 허용
        BoundedBox.addBox(numberList, 10);
        BoundedBox.addBox(numberList, 20);
        BoundedBox.addBox(numberList, 30);
        System.out.println("addBox 결과: " + numberList);
    }
}
