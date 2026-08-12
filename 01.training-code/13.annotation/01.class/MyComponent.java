import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스, 인터페이스, enum
@Retention(RetentionPolicy.RUNTIME)
public @interface MyComponent {
    String value() default ""; // 이름 속성
}
