import java.lang.reflect.Field;

public class ReflectionField {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Person");
        Object obj = clazz.getDeclaredConstructor().newInstance();

        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true); // private 필드 접근 허용

        field.set(obj, "홍길동");           // 값 설정
        System.out.println(field.get(obj)); // 값 읽기
    }
}
