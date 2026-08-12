public class Main {
    public static void main(String[] args) {
        // 객체 생성 (인스턴스화)
        Stock scalaEdu = new Stock("스칼라 에듀", 15000);
        Stock scalaAI = new Stock("스칼라 AI", 17500);

        // 객체 상태 변경
        scalaEdu.updatePrice(15800);
        scalaEdu.printInfo();
        scalaAI.printInfo();
    }
}
