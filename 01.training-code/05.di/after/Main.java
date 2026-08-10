public class Main {
    public static void main(String[] args) {
        Coffee hot = new Coffee(new Hot());
        hot.coffeeType();

        Coffee ice = new Coffee(new Ice());
        ice.coffeeType();

        // ThinIce가 추가돼도 Coffee.java는 한 줄도 수정하지 않았다
        Coffee thinIce = new Coffee(new ThinIce());
        thinIce.coffeeType();
    }
}
