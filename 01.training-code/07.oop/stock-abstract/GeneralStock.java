public class GeneralStock extends Stock {

    public GeneralStock(String name, double price) {
        super(name, price);
    }

    @Override
    public void printInfo() {
        System.out.println("[일반주] 종목: " + name + ", 가격: " + price + "원");
    }
}
