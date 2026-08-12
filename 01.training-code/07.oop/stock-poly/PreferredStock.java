public class PreferredStock extends Stock {
    private double dividendRate;

    public PreferredStock(String name, double price, double dividendRate) {
        super(name, price);
        this.dividendRate = dividendRate;
    }

    // 메서드 오버라이딩
    @Override
    public void printInfo() {
        System.out.println(
                "[우선주] 종목: " + getName() +
                        ", 가격: " + getPrice() +
                        "원, 배당률: " + dividendRate + "%");
    }

    // 메서드 오버로딩
    public void printInfo(String prefix) {
        System.out.println(
                prefix + "[우선주] 종목: " + getName() +
                        ", 가격: " + getPrice() +
                        "원, 배당률: " + dividendRate + "%");
    }
}
