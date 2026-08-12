public class PreferredStock implements Stock {
    private final String name;
    private final double price;
    private final double dividendRate;

    public PreferredStock(String name, double price, double dividendRate) {
        this.name = name;
        this.price = price;
        this.dividendRate = dividendRate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void printInfo() {
        System.out.println("[우선주] 종목: " + getName() + ", 가격: " + getPrice() + "원, 배당률: " + dividendRate + "%");
    }
}
