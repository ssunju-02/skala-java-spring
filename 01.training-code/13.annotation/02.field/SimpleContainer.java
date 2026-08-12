import java.lang.reflect.Field;

public class SimpleContainer {
    public static void injectDependencies(Object target) throws Exception {
        Class<?> clazz = target.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                // 인스턴스를 만들어서 주입 (아주 단순한 예)
                Object dependency = field.getType().getDeclaredConstructor().newInstance();
                field.setAccessible(true);
                field.set(target, dependency);
            }
        }
    }
}
