package understand;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * java.lang.StringBuilder
 * 1.该修改是线程不安全的，在多线程环境下使用时需要加锁
 * 2.StringBuilder的底层维护一个byte[](super class)
 * 它存储了字符串的内容，这个byte[]数组可以存储char和byte
 * 当String.COMPACT_STRINGS为true时,value数组存储的是byte类型的数据
 * 每个byte元素代表一个char的低8位.这意味着每个 char 需要两个byte来存储。
 * 当String.COMPACT_STRINGS为false时,value数组存储的是char类型的数据
 * 每个 char 元素直接存储在 byte[] 数组中。
 * 3.自动扩容，修改速度和性能开销优异，并且提供了修改字符串的常见对应操作方法：增删改查
 * 使用动态数组的思想(其实就是手动的扩容和复制char[]数组，并不是使用了Vector集合对象)
 * <p>
 * stringBuilder and stringBuffer
 * stringBuilder是线程不安全的,并发处理的，性能稍快
 * stringBuffer是线程安全的,同步处理的，性能稍慢
 * <p>
 * regexp
 * using regexp to match string
 * basic grammar
 * [X]: express a set of characters
 * ".": any character
 * \d: any digit
 * \w: any word character[a-zA-Z0-9_]
 * \s: any whitespace character[ \t\n\r\f]
 * \D: any non-digit
 * \W: any non-word character
 * \S: any non-whitespace character
 * measure words
 * ?: zero or one
 * +: one or more
 * *: zero or more
 * {n}: exactly n
 * {n,m}: at least n, at most m
 * (): capture group
 * |: logical or & logical
 * ^: start of line $: end of line
 * + concat
 * <p>
 * java.lang.Object
 * super class of all classes
 * toString(),equals(),hashCode(),getClass(),clone(),finalize()
 * notify(),notifyAll(),wait(),wait(long timeout),wait(long timeout,int nanos)
 * <p>
 * 包装类
 * java define 8 packaging classes
 * Boolean,Character,Byte,Short,Integer,Long,Float,Double
 * 其中数字类型的包装类都继承自java.lang.Number,而char和boolean的包装类直接继承自Object
 * Number是一个抽象类,定义了一些方法,目的是让包装类可以将其表示的基本类型转换为其他数字类型
 * 除了浮点的包装类，其他类都有常量池
 * 整数类型默认的复用范围是 -128~127
 * Boolean false,true Character 0~127
 */
public class LearnSecond {
    @Test
    public void testPackagingClass() {
        Integer i1 = Integer.valueOf(1);
        Integer i2 = Integer.valueOf(1);
        System.out.println(i1 == i2);
        Double d1 = Double.valueOf(123.121d);
        Double d2 = Double.valueOf(123.121d);
        System.out.println(d1 == d2);
        Character c1 = Character.valueOf('g');
        Character c2 = Character.valueOf('g');
        System.out.println(c1 == c2);
        Long l1 = Long.valueOf(-128L);
        Long l2 = Long.valueOf(-128L);
        System.out.println(l1 == l2);
        //字符串变成数值类型 -- parseXXX
        //public static method parseXXX(String str);
        int i3 = Integer.parseInt("123");
        System.out.println(i3);
        double d3 = Double.parseDouble("123.123");
        System.out.println(d3);
        long l3 = Long.parseLong("-128");
        System.out.println(l3);
        //auto boxing and unboxing
        /*
        触发自动拆箱特性,编译器会补充代码将包装类转换为基本类型,下面的代码会变为:
        int i = new Integer(123).intValue();
        */
        int i = new Integer(123);
        /*
        触发编译器自动装箱特性,代码会被编译器改为:
        Integer in = Integer.valueOf(123);
        */
        Integer in = 123;
    }

    @Test
    public void testStringBuilderC() {
        String str = "a";
        long StimeB = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            str += "a";
        }
        long StimeE = System.currentTimeMillis();
        System.out.println("String time:" + (StimeE - StimeB) + "ms");
        StringBuilder sb = new StringBuilder("a");
        long SbtimeB = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");
        }
        long SbtimeE = System.currentTimeMillis();
        System.out.println("StringBuilder time:" + (SbtimeE - SbtimeB) + "ms");
        //test email
        String mail = "jds@qq.com";
        String regexp = "^[a-zA-Z0-9]+@[\\w]{2,}.[\\w]{2,}$";
        //public Boolean matches(String regex)
        if (mail.matches(regexp)) {
            System.out.println("is a mail");
        } else {
            System.out.println("is not a mail");
        }
        //public String[] split(String regex)
        //spliting by matching regex is equivalence to replace matching
        //regex position using ,
        System.out.println(Arrays.toString(regexp.split("\\w")));
        //public String replaceAll(String regex, String replacement)
        //replace all matching regex with replacement
        System.out.println(mail.replaceAll("\\w", "*"));
        //直接重写String方法--匿名内部类实现
        Object obj = new Object() {
            public String toString() {
                return "any type concat with string is equivalence to toString";
            }
        };
        //类全名@地址 -- object默认打印方法
        System.out.println(obj);
        /*
        any type concat with string is equivalence to toString
         */
        String line = "current object is " + obj;
        System.out.println(line);

    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder("abc");
        sb.append("aaa");
    }
}
