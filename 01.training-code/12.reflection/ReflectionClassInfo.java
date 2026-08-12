import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionClassInfo {
    public static void main(String[] args) throws Exception {
        // 1) 클래스 리터럴
        Class<Person> clazz1 = Person.class;

        // 2) 객체에서 가져오기
        Person p = new Person();
        Class<?> clazz2 = p.getClass();

        // 3) 문자열로부터 로딩 (동적 로딩)
        Class<?> clazz3 = Class.forName("Person");

        System.out.println("clazz1 == clazz2: " + (clazz1 == clazz2));
        System.out.println("clazz2 == clazz3: " + (clazz2 == clazz3));

        System.out.println("클래스 이름: " + clazz1.getName());
        System.out.println("슈퍼 클래스: " + clazz1.getSuperclass());

        System.out.println("=== Fields ===");
        for (Field field : clazz1.getDeclaredFields()) {
            System.out.println(field);
        }

        System.out.println("=== Methods ===");
        for (Method method : clazz1.getDeclaredMethods()) {
            System.out.println(method);
        }

        System.out.println("=== Constructors ===");
        for (Constructor<?> constructor : clazz1.getDeclaredConstructors()) {
            System.out.println(constructor);
        }
    }
}
