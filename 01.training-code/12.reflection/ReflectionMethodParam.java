import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReflectionMethodParam {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Person");
        Method method = clazz.getMethod("sayHello", String.class);

        // 파라미터 정보 확인
        for (Parameter parameter : method.getParameters()) {
            System.out.println("파라미터 이름: " + parameter.getName() + ", 타입: " + parameter.getType());
        }

        // 파라미터를 포함하여 메서드 호출
        Object obj = clazz.getDeclaredConstructor(String.class).newInstance("스칼라");
        method.invoke(obj, "홍길동");
    }
}
