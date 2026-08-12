public class PreferredStock extends Stock {
    private double dividendRate;

    public PreferredStock(String name, double price, double dividendRate) {
        super(name, price);
        this.dividendRate = dividendRate;
    }

    @Override
    public void printInfo() {
        System.out.println("[우선주] 종목: " + name + ", 가격: " + price + "원, 배당률: " + dividendRate + "%");
    }
}
