public class MyCalculator extends Calculator {

    @Override
    protected double calculate(double a, String operator, double b) {
        if (operator.equals("/") && b == 0) {
            throw new ArithmeticException("0으로 나눌 수 없습니다.");
        }

        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> Double.NaN;
        };
    }

    @Override
    protected void printHistory() {
        System.out.println("=== 계산 기록 ===");
        for (String record : history) {
            if (record != null) {
                System.out.println(record);
            }
        }
    }
}
