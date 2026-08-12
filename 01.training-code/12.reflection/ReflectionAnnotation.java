import java.lang.reflect.Method;

public class ReflectionAnnotation {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("Person");

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Note.class)) {
                Note note = method.getAnnotation(Note.class);
                System.out.println(method.getName() + " -> @Note: " + note.value());
            }
        }
    }
}
