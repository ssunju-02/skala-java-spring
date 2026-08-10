public class Coffee {
    private final Ame ame;

    // 외부에서 구현체를 주입받음 (생성자 주입)
    public Coffee(Ame ame) {
        this.ame = ame;
    }

    // 새로운 커피 추가 시에도 이 코드는 전혀 바뀌지 않는다
    public void coffeeType() {
        ame.get();
    }
}
