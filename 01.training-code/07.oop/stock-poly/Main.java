public class Main {

    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        Stock stock1 = new Stock("삼성전자", 80000);
        PreferredStock stock2 = new PreferredStock("LG전자", 60000, 5.0);

        stock1.printInfo();

        // 오버라이딩된 printInfo() 호출
        stock2.printInfo();

        // 오버로딩된 printInfo(String) 호출
        stock2.printInfo("[알림] ");

        // 업캐스팅 상태에서도 오버라이딩된 메서드가 호출됨
        printStockInfo(new PreferredStock("Netflix", 60000, 10.0));
    }
}
