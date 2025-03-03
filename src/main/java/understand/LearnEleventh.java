package understand;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * <p>
 * Reflection
 * 概念
 * 将类、方法、属性、构造器、包等和类相关的实体，抽象为相关类，通过这些类获取信息进行操作
 * 从而实现对类的间接控制,这样的技术称为反射,在框架级别的程序开发中非常重要
 * Class类
 * Instances of the class Class represent classes and interfaces in a
 * running Java application. An enum is a kind of class and
 * an annotation is a kind of interface.
 * Every array also belongs to a class that is reflected as a Class object
 * that is shared by all arrays with the same element type and number of dimensions.
 * The primitive Java types (boolean, byte, char, short, int, long, float, and double),
 * and the keyword void are also represented as Class objects.
 * <p>
 * Class类
 * 1.获取class
 * 类名.class
 * 对象.getClass()
 * Class.forName("类的全路径名")
 * 2.获取类名
 * getName()getSimpleName()
 * 3.获取父类
 * getSupperClass()
 * 4.获取包
 * getPackage()
 * 5.获取构造器
 * getConstructor(Class ...parameterTypes) getConstructors()
 * 6.获取方法
 * getMethod(String name, Class...parameterTypes)
 * getMethods()
 * 7.获取属性
 * getField(String name)
 * getFields()
 * 8.获取接口
 * getInterfaces()
 * 9.创建实例
 * newInstance() --已被弃用
 * 10.获取类加载器
 * getClassLoader()
 * 11.获取注解
 * getAnnotations() getAnnotation() getDeclaredAnnotations() getDeclaredAnnotation() --已被弃用
 * <p>
 * Package类
 * 1.获取包名
 * getName()
 * 2.获取包下的类
 * getClasses()
 * <p>
 * Constructor类
 * 1.创建对象
 * newInstance(Object...initargs)
 * <p>
 * Field类
 * 1.获取字段的值
 * get(Object obj)
 * 2.设置字段的值
 * set(Object obj, Object value)
 * 3.获取属性名
 * getName()
 * 4.获取属性类型
 * getType() --已被弃用
 * Method类
 * 1.执行方法
 * invoke(Object obj, Object...args)
 * 2.获取方法名
 * getName()
 * 3.获取参数列表
 * getParameterTypes()
 * 4.获取返回值类型
 * getReturnType()
 */
@SuppressWarnings("all")
public class LearnEleventh {
    @Test
    public void testClass() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        //1.获取Class
        Class<LearnEleventh> learnEleventhClass = null;
        //learnEleventhClass = LearnEleventh.class;
        //learnEleventhClass = new LearnEleventh().getClass();
        learnEleventhClass = (Class<LearnEleventh>) Class.forName("understand.LearnEleventh");
        //2.获取类名
        System.out.println(learnEleventhClass.getName());
        System.out.println(learnEleventhClass.getSimpleName());
        //3.获取父类
        Class<? super LearnEleventh> superclass = learnEleventhClass.getSuperclass();
        System.out.println(superclass.getName());
        //4.获取包
        learnEleventhClass.getPackage();
        //5.获取构造器
        learnEleventhClass.getConstructors();
        // learnEleventhClass.getConstructor(String.class);
        //6.获取方法
        learnEleventhClass.getMethods();
        learnEleventhClass.getMethod("testClass", null);
        //7.获取属性
        learnEleventhClass.getFields();
        // learnEleventhClass.getField("learnEleventhClass");
        //8.获取接口
        learnEleventhClass.getInterfaces();
        //9.创建实例
        learnEleventhClass.newInstance();
        //10.获取类加载器
        learnEleventhClass.getClassLoader();
        //11.获取注解
        learnEleventhClass.getAnnotations();
        learnEleventhClass.getDeclaredAnnotations();
        learnEleventhClass.getAnnotation(Deprecated.class);
    }

    @Test
    public void testPackage() {
        Package aPackage = LearnEleventh.class.getPackage();
        System.out.println(aPackage.getName());
        System.out.println(aPackage.getClass());
        System.out.println(aPackage.getClass().getName());
    }

    @Test
    public void testConstructor() {
        try {
            Constructor<LearnEleventh> constructor = LearnEleventh.class.getConstructor();
            LearnEleventh learnEleventh = constructor.newInstance();
            System.out.println(learnEleventh);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMethod() {
        try {
            Method method = LearnEleventh.class.getMethod("testClass");
            method.invoke(new LearnEleventh(), null);
            System.out.println(method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testField() {
        try {
            Field field = LearnEleventh.class.getField("learnEleventhClass");
            System.out.println(field.get(null));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
