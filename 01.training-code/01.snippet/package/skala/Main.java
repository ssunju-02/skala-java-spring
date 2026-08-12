package skala;

import skala.domain.Stock;

public class Main {
    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        printStockInfo(new Stock("스칼라 AI", 17500));
    }
}
