package exercise.annotation.dao;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class MyDaoFactory {
    public static MyDao createMyDao() {
        List<Class> list = new ArrayList();
        //获取exercise.annotation.dao包下的所有类
        URL url = MyDaoFactory.class.getClassLoader().getResource("./exercise/annotation/dao");
        try {
            File dir = new File(url.toURI());
            File[] files = dir.listFiles();
            for(File file : files) {
                if(file.isFile() && file.getName().endsWith(".class")){
                    //截取他们的文件名,拼上包名,得到类的全路径名
                    String shortName = file.getName().split("\\.")[0];
                    String fullName = "exercise.annotation.dao." + shortName;
                    Class<?> clazz = Class.forName(fullName);
                    list.add(clazz);
                    for(Class clz : list){
                        if(clz.isAnnotationPresent(MyDBMS.class)){
                            return(MyDao) clz.newInstance();
                        }
                    }
                }
            }
        } catch (URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e);
        }
        return null;
    }
    private static Properties prop = null;
    static{
        try {
            //File file = new File("./my.properties");
           // System.out.println(file.exists());
            InputStream in = MyDaoFactory.class.getClassLoader().getResourceAsStream("my.properties");
            prop = new Properties();
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取配置文件,根据配置文件生成对应的MyDao,返回
     */
    public static MyDao getDao(){
        try {
            //获取配置信息
            String cStr = prop.getProperty("MyDao");
            //创建该类的对象返回
            Class clz = Class.forName(cStr);
            Constructor constructor = clz.getConstructor();
            return (MyDao) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
