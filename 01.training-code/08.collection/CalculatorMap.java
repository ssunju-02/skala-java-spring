import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class CalculatorMap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, String> historyMap = new HashMap<>();
        boolean repeat = true;

        while (repeat) {
            System.out.print("첫 번째 숫자: ");
            double firstNumber = Double.parseDouble(scanner.nextLine());

            System.out.print("연산자(+ - * /): ");
            String operator = scanner.nextLine();

            System.out.print("두 번째 숫자: ");
            double secondNumber = Double.parseDouble(scanner.nextLine());

            try {
                if (operator.equals("/") && secondNumber == 0) {
                    throw new ArithmeticException("0으로 나눌 수 없습니다.");
                }

                double result = switch (operator) {
                    case "+" -> firstNumber + secondNumber;
                    case "-" -> firstNumber - secondNumber;
                    case "*" -> firstNumber * secondNumber;
                    case "/" -> firstNumber / secondNumber;
                    default -> Double.NaN;
                };
                String record = firstNumber + " " + operator + " " + secondNumber + " = " + result;
                System.out.println(record);
                historyMap.put(record.hashCode(), record);
            } catch (ArithmeticException e) {
                System.out.println("예외 발생: " + e.getMessage());
            }

            System.out.print("계속하려면 c, 종료하려면 q를 입력하세요: ");
            repeat = scanner.nextLine().equalsIgnoreCase("c");
        }

        System.out.println("\n=== 계산 기록 ===");
        Iterator<Map.Entry<Integer, String>> iterator = historyMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }
}
