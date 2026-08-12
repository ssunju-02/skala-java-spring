import java.util.Scanner;

public abstract class Calculator {
    protected String[] history = new String[100];
    protected int historyCount = 0;

    // 입력 -> 계산 -> 이력 저장의 전체 흐름은 부모 클래스에서 표준화
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.print("첫 번째 숫자: ");
            double firstNumber = Double.parseDouble(scanner.nextLine());

            System.out.print("연산자(+ - * /): ");
            String operator = scanner.nextLine();

            System.out.print("두 번째 숫자: ");
            double secondNumber = Double.parseDouble(scanner.nextLine());

            try {
                double result = calculate(firstNumber, operator, secondNumber);
                String record = firstNumber + " " + operator + " " + secondNumber + " = " + result;
                System.out.println(record);
                if (historyCount < history.length) {
                    history[historyCount++] = record;
                }
            } catch (ArithmeticException e) {
                System.out.println("예외 발생: " + e.getMessage());
            }

            System.out.print("계속하려면 c, 종료하려면 q를 입력하세요: ");
            repeat = scanner.nextLine().equalsIgnoreCase("c");
        }

        printHistory();
        scanner.close();
    }

    // 세부 계산 방식은 하위 클래스에서 구현
    protected abstract double calculate(double a, String operator, double b);

    // 이력 출력 방식은 하위 클래스에서 구현
    protected abstract void printHistory();
}
