package tool;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Properties;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@MyFile(fileName = "config.properties")
public abstract class ReadFile {
    private static Properties prop = new Properties();
    static {
        MyFile annotation = ReadFile.class.getAnnotation(MyFile.class);
        try {
            prop.load(ReadFile.class.getResourceAsStream("/"+annotation.fileName()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private ReadFile(){}
    public static String getValue(String key){
        return prop.getProperty(key);
    }
    public void setProperties(Properties prop){
        ReadFile.prop = prop;
    }
}
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface MyFile{
    String fileName();
}
