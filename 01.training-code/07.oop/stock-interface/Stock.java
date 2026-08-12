public interface Stock {
    String getName();

    double getPrice();

    default void printInfo() {
        System.out.println("[일반주] 종목: " + getName() + ", 가격: " + getPrice() + "원");
    }
}
