package understand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jds
 * @version 1.1
 * @Target-限定当前注解的作用范围 ElementType.TYPE, ElementType.METHOD, ElementType.FIELD
 * ElementType.ANNOTATION_TYPE等
 * @Retention-限定当前注解的生命周期 RetentionPolicy.SOURCE 编译阶段
 * RetentionPolicy.CLASS  加载类阶段
 * RetentionPolicy.RUNTIME 运行时都存在
 * 见图解
 * <p>
 * 声明属性
 * 声明注解属性的过程非常类似于为接口定义方法
 * 访问权限只能是public的,不写默认就是public
 * 类型只能是八种基本数据类型 String类型 class类型 枚举类型 其它注解类型 及 以上类型的一维数组
 * 可以在属性的声明之后,通过default关键字为属性声明默认值.
 * 有默认值的属性可以不赋值,不赋值则取默认值
 * 为注解声明的属性,如无默认值,必须在使用注解时,明确指定属性的值
 * 在注解使用时,后跟小括号,在小括号内通过属性名=属性值的方式为注解属性赋值
 * 如果属性是数组类型,则需要通过{}包裹所有值
 * 如果数组中值只有一个,则包裹数组的大括号可以省略
 * 如果需要赋值的值只有一个,且名字叫value,则赋值时,value=可以省略
 * <p>
 * 反射注解
 * 通过反射注解可以获取组件上注解的信息,从而控制程序按照不同方式运行
 * boolean isAnnotationPresent(Class anno) -- 判断当前类上是否有指定类型的注解
 * Annotation [] getAnnotations(); -- 获取当前类上声明的所有注解
 * Annotation getAnnotation(Class annoClz); -- 获取当前类上的指定类型的注解
 * @since 1.0.0
 * <p>
 * Annotation
 * 注释:给人看的提示信息
 * 注解:给程序看的提示信息
 * 可以根据 有没有注解 或 注解上的属性值的不同 决定程序按照不同的方式去运行
 * 本质上是对程序进行的配置,是对配置文件的一种替代方案
 * 相对于配置文件,它更加轻量级,简单 方便 易用,便于管理
 * 现代开发中,很多时候,配置文件用于配置核心的选项,而注解用于配置其它轻量级配置.
 * jdk原生注解:@Deprecated,@Override,@SuppressWarnings等
 * 自定义注解:public @interface AnnotationName{}
 * <p>
 * 元注解标注
 * 是java官方提供的,用于定义注解特性的注解.所以称为"元"注解
 */
public class LeanTwelfth {
}

/**
 * 实现一个简单的警察查车辆的不同判断(基于Annotation)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface PL {
    PoliceType value() default PoliceType.协警;
}

enum PoliceType {
    协警, 交警, 刑警, 警督
}

@PL(PoliceType.警督)
class Police {
    public void punishCash() {
        System.out.println("罚款两百元！");
    }

    public static void main(String[] args) {
        Police p = new Police();
        p.punishCash();
        if (p.getClass().isAnnotationPresent(PL.class)) {
            PoliceType pType = p.getClass().getAnnotation(PL.class).value();
            switch (pType) {
                case 协警:
                    System.out.println("老哥来根烟,能不能只罚50元,大家混生活都不容易");
                    break;
                case 交警:
                    System.out.println("哥,这包芙蓉王拿去抽，通融下，只罚100元行吗？");
                    break;
                case 刑警:
                    System.out.println("赶紧交钱走人");
                    break;
                case 警督:
                    System.out.println("sir,来包和天下,是不是罚少了,来,500拿去");
                    break;
            }
        } else {
            System.out.println("冒充警察？直接硬刚！");
        }
    }
}

