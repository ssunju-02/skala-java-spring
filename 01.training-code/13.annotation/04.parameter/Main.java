import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Main {
    public static void main(String[] args) throws Exception {
        Method method = UserController.class.getMethod("createUser", String.class, int.class);
        Parameter[] params = method.getParameters();

        for (Parameter p : params) {
            ParamName ann = p.getAnnotation(ParamName.class);
            if (ann != null) {
                System.out.println("실제 파라미터 이름: " + p.getName() + ", Annotation 이름: " + ann.value());
            }
        }
    }
}
