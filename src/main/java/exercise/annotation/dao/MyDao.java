package exercise.annotation.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface MyDao {
    void add();
    void get();
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface MyDBMS{
}