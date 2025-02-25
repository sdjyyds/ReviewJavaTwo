package understand;

import org.junit.Test;
import tool.Scan;

import java.util.Arrays;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * document comments
 * 文档注释是功能级注释，用来说明一个类，一个方法或一个常量的，因此只在上述三个地方使用。
 * 文档注释可以使用java自带的命令javadoc来对这个类生成手册。
 * <p>
 * String类
 * 现阶段理解：
 * 1.存在与java.lang.object包下，public final class String extends Object
 * String实例是不变对象，不能改变其值，否则就会重新创建String实例，导致性能急剧下降
 * R:实现大量的字符串对象的重用来减小内存开销
 * 可以保证String对象的线程安全和哈希值的稳定性，
 * 如何实现的：
 * public String concat(String str) {
 * int otherLen = str.length();
 * if (otherLen == 0) {
 * return this;
 * }
 * char buf[] = new char[count + otherLen];
 * getChars(0, count, buf, 0);
 * str.getChars(0, otherLen, buf, count);
 * return new String(buf, true);
 * }
 * 2.String类内部维护一个默认长度为1024(2^10)长度的char数组,且该数组也被final了(内容可变，但长度不可变)
 * 不对，String 类的 char 数组创建时默认长度不是 1024。
 * 在 Java 中，String 类的 char 数组的长度是根据字符串的长度动态创建的。也就是说，当你创建一个 String对象时，
 * char 数组的长度就是字符串的长度。
 * 如果你存储的数据超过了 char 数组的长度，String 类会自动创建一个新的 char 数组
 * 长度为原来的长度加上需要存储的数据长度。具体来说，当你使用 String 类的 concat 方法或 + 运算符拼接字符串时，
 * String 类会创建一个新的 char 数组，长度为原来的长度加上需要存储的数据长度，
 * 然后将原来的数据和新数据复制到新的 char 数组中。例如，如果你有一个 String 对象 s
 * 其 char 数组长度为 10，你使用 s.concat("hello") 方法拼接一个长度为 5 的字符串
 * 则 String 类会创建一个新的 char 数组，长度为 15（10 + 5）
 * 然后将原来的数据和新数据复制到新的 char 数组中。
 * 所以，String 类的 char 数组长度不是固定的，而是根据字符串的长度动态创建和调整的。
 * 3.字符串底层封装了字符数组及针对字符数组的操作算法；
 * 4.字符串一旦创建，对象永远无法改变，但字符串引用可以重新赋值；
 * 5.Java字符串在内存中采用Unicode编码方式，任何一个字符对应两个字节的定长编码。
 * 6.字符串常量池的位置
 * Java 1.7 之前：字符串常量池存放在永久代（PermGen）中。
 * Java 1.7，1.8：字符串常量池仍然位于堆中，而不是元空间（Metaspace）。
 * 7.常量池（Constant Pool）是类文件中的一个数据结构，用于存储类、接口、方法等的常量信息
 * 包括字符串常量、整数常量、浮点数常量等。常量池的大小是由类文件的格式决定的
 * 8，只有通过字面量的直接赋值的形式，才能在常量池中创建字符串常量
 *
 */
public class LearnFirst {
    public final static StringBuilder sb = new StringBuilder();
    @Test()
    public void testString() {
        //String s1 = "hello"; // 在常量池中创建字符串常量
        String s2 = new String("hello"); // 不在常量池中创建字符串常量
        //s3,s4可重用常量池中hello
        String s3 = "he" + "llo"; // 不在常量池中创建字符串常量
        String s4 = "hello";
        System.out.println(s2 == s3);//false
        System.out.println(s3 == s4);//true
//        System.out.println(s1 == s2);
//        System.out.println(s1 == s3);
//        System.out.println(s1 == s4);
        sb.append("123abc");
        s2 = sb.toString();
        String s = "123";
        String s6 = s + "abc";
        System.out.println(s2==s6);//false -- 编译器无法计算出 s + "abc"//所以无法重用
        String s9 = 1+"2"+3+"abc";
        System.out.println(s2==s9);//true
        String line ="www.tedu.cn";
        String str = line.substring(4,8);
        System.out.println(str);
        sb.delete(0,sb.length());
        sb.append("     helo ");
        System.out.println(sb.length());
        System.out.println(sb.toString().trim());
        sb.delete(0,sb.length());
        sb.append("thinking in java");
        char c = sb.charAt(0);
        System.out.println(c);
        sb.delete(0,sb.length());
        line = "http://www.tedu.cn";
        System.out.println(line.startsWith("http:"));
        System.out.println(line.endsWith(".cn"));
        line = "I love java";
        System.out.println(line.toLowerCase());
        System.out.println(line.toUpperCase());
        int a = 111;
        //String.valueof(E)，将其他类型转化为字符串，重载和泛型机制
        System.out.println(String.valueOf(a).getClass().getName());


    }

    public void testFinalArray() {
        final char[] ch = new char[15];
        ch[0] = 'a';
        ch[0] = 'b';
        char[] ch2 = new char[20];
        //被final修饰的数组指向的空间不能变，可以改变数组内部值
        //ch = ch2;
    }
}

