import java.lang.reflect.Constructor;

public class ReflectionConstructor {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Person");

        // 기본 생성자 호출
        Object obj1 = clazz.getDeclaredConstructor().newInstance();
        System.out.println("obj1 = " + obj1);

        // (String) 생성자 호출
        Object obj2 = clazz.getDeclaredConstructor(String.class).newInstance("himang10");
        System.out.println("obj2 = " + obj2);

        // (String, int) 생성자 호출
        Constructor<?> constructor = clazz.getConstructor(String.class, int.class);
        Object obj3 = constructor.newInstance("홍길동", 20);
        System.out.println("obj3 = " + obj3);
    }
}
