import java.lang.reflect.Method;

public class ReflectionMethod {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Person");
        Object obj = clazz.getDeclaredConstructor(String.class).newInstance("스칼라");

        // sayHello(String name) 메서드 호출
        Method method = clazz.getMethod("sayHello", String.class);
        method.invoke(obj, "홍길동");
    }
}
