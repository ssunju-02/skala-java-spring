public class Main {

    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        Stock stock1 = new Stock("삼성전자", 80000);
        Stock stock2 = new PreferredStock("LG전자", 60000, 5.0);

        stock1.printInfo();
        stock2.printInfo();

        // 업캐스팅: PreferredStock -> Stock 파라미터로 전달해도 오버라이딩된 메서드가 실행됨
        printStockInfo(new Stock("SKALA", 80000));
        printStockInfo(new PreferredStock("Netflix", 60000, 10.0));
    }
}
