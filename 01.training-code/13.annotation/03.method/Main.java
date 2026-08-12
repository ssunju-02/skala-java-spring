import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws Exception {
        TaskRunner runner = new TaskRunner();
        Class<?> clazz = runner.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        // @RunMe가 붙은 메서드만 추출 후 order 순으로 정렬
        Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(RunMe.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(RunMe.class).order()))
                .forEach(m -> {
                    try {
                        m.invoke(runner); // 메서드 호출
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
