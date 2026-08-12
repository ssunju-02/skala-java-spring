public class Main {
    public static void main(String[] args) throws Exception {
        OrderController controller = new OrderController();
        SimpleContainer.injectDependencies(controller);
        controller.handle();
    }
}
