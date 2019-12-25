package annotation;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorAnno{
    String name() default "Huang Jiaying";
    String website() default "cn.edu.nju";
    int revision() default 1;
}
