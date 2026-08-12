public class OrderController {
    @Inject
    private OrderService orderService; // 주입 대상

    public void handle() {
        orderService.placeOrder();
    }
}
