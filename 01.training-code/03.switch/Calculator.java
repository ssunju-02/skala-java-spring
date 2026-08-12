import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("첫 번째 숫자: ");
        double firstNumber = Double.parseDouble(scanner.nextLine());

        System.out.print("연산자(+ - * /): ");
        String operator = scanner.nextLine();

        System.out.print("두 번째 숫자: ");
        double secondNumber = Double.parseDouble(scanner.nextLine());

        if (operator.equals("/") && secondNumber == 0) {
            System.out.println("0으로 나눌 수 없습니다.");
            return;
        }

        double result = switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> firstNumber / secondNumber;
            default -> Double.NaN; // 잘못된 연산자인 경우 NaN 반환
        };

        System.out.println(firstNumber + " " + operator + " " + secondNumber + " = " + result);

        scanner.close();
    }
}
