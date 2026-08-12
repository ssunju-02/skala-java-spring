public abstract class Stock {
    protected String name;
    protected double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void printInfo();
}
