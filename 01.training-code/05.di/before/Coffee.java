public class Coffee {
    private String kind;
    private Ame ame;

    public Coffee(String kind) {
        this.kind = kind;
    }

    public void coffeeType() {
        if (kind.equals("hot")) {
            ame = new Hot();
        } else if (kind.equals("ice")) {
            ame = new Ice();
        } else if (kind.equals("thinIce")) {
            // ThinIce를 추가하려면 Coffee.java 자체를 수정해야 한다 (OCP 위반)
            ame = new ThinIce();
        }
        ame.get();
    }
}
