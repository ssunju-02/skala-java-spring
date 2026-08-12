import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.print("첫 번째 숫자: ");
            double firstNumber = Double.parseDouble(scanner.nextLine());

            System.out.print("연산자(+ - * /): ");
            String operator = scanner.nextLine();

            System.out.print("두 번째 숫자: ");
            double secondNumber = Double.parseDouble(scanner.nextLine());

            if (operator.equals("/") && secondNumber == 0) {
                System.out.println("0으로 나눌 수 없습니다.");
            } else {
                double result = switch (operator) {
                    case "+" -> firstNumber + secondNumber;
                    case "-" -> firstNumber - secondNumber;
                    case "*" -> firstNumber * secondNumber;
                    case "/" -> firstNumber / secondNumber;
                    default -> Double.NaN;
                };
                System.out.println(firstNumber + " " + operator + " " + secondNumber + " = " + result);
            }

            System.out.print("계속하려면 c, 종료하려면 q를 입력하세요: ");
            String choice = scanner.nextLine();
            repeat = choice.equalsIgnoreCase("c");
        }

        System.out.println("계산기를 종료합니다.");
        scanner.close();
    }
}
