public class Main {
    public static void main(String[] args) {
        Class<?> clazz = OrderService.class;

        if (clazz.isAnnotationPresent(MyComponent.class)) {
            MyComponent comp = clazz.getAnnotation(MyComponent.class);
            System.out.println("컴포넌트 이름 = " + comp.value());
        }
    }
}
