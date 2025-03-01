package understand;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * Throwable
 * this is a super class of Exception and Error
 * Error - representation of unrecoverable errors(system errors)
 * including outOfMemoryError,StackOverflowError
 * Exception - representation of being handled by users
 * classify RuntimeException and checked Exception
 * RuntimeException including NullPointerException,ArithmeticException,ArrayIndexOutOfBoundsException
 * CheckedException including IOException,SQLException
 * <p>
 * 检查异常：编译阶段，编译器能够检测到异常，这种异常必须处理，处理的方式，要么捕获，要么
 * 继续抛出。
 * 非检查异常：编译阶段无法检测到的异常，程序员对此类异常可以有选择性的处理。
 * 典型代表为RuntimeException或它的子类。
 * <p>
 * 自关闭特性
 * 我们创建的对象需要关闭时，一种方法是写在finally中，还有一种方式在try()接口中直接创建，
 * 这样对象无需手动再关闭。前提时try内部的对象需要实现 AutoCloseable 接口。
 * <p>
 * custom exception
 * 当系统提供的异常类型，不足以满足我们对异常的需求时，我们可以选择自定义异常，尤其是
 * 和业务相关性比较的异常。 例如PhoneNotFoundException;
 * 我们自定义异常一般都是继承RuntimeException或Exception类型，然后重写相关方法
 */
class PhoneNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5335353533L;

    public PhoneNotFoundException() {
    }

    public PhoneNotFoundException(String message) {
        super(message);
    }

    public PhoneNotFoundException(Throwable cause) {
        super(cause);
    }
}

public class LearnSeventh {
    @Test
    public void testThrows() throws IOException {
        FileInputStream fis = new FileInputStream("a.png");
        FileOutputStream fos = new FileOutputStream("c.png");
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        fos.write(buf);
        fis.close();
        fos.close();
        System.out.println("copy ok");
    }

    static void doCompute01(int a, int b) {
        if (b == 0) {
            //抛出参数无效的异常,这里用户传什么参数我们决定不了
            //只能告诉用户你传递的参数是非法的，是不合理的。
            throw new IllegalArgumentException("除数不能为0");
        }
        int result = a / b;
        System.out.println(result);
    }

    static void doCompute02(int a, int b) {
        try {
            int result = a / b;
            System.out.println(result);
        } catch (ArithmeticException e) {
            //这里以后是日志记录
            System.out.println("除数不能为0");
            throw e;//处理以后，可以继续抛给调用着
        }
        System.out.println("==finish==");
    }

    public static void main(String[] args) {
        //doCompute01(10, 0);
        doCompute02(10, 0);
    }

    @Test
    public void testAutoCloseable() {
        try (
                FileOutputStream fos =//这里构建对象无需手动关闭
                        new FileOutputStream("./src/f1.txt", true);
        ) {
            //1.构建输出流对象(编译阶段能够检测到的异常我们通常称之为检查异常)
            //2.写数据到文件
            String s = "hello";
            fos.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException | NullPointerException e) {
            // System.out.println("文件没找到:"+e.getMessage());
            e.printStackTrace();//打印异常栈信息(包含的异常信息会更全面)
            //return;//遇到return语句时，是先finally，然后再返回
            //System.exit(-1);这条语句执行时，finally不在执行
        } catch (IOException e) {
            System.out.println("写数据或关闭流时出现了问题:" + e.getMessage());
        }
    }

    @Test
    public void testFinal() {
        try {
            String str = null;
            //str.matches("(^[\\w]*$)");
            char ch[] = new char[2];
            ch[3] = 'a';
        } catch (RuntimeException e) {
            System.out.println(e);
        } finally {
            System.out.println("finally");
        }
        FileOutputStream fos = null;
        File file = null;
        String s = null;
        try {
            //1.构建输出流对象(编译阶段能够检测到的异常我们通常称之为检查异常)
            file = new File("./src/tt/f1.txt");
            file.createNewFile();
            fos = new FileOutputStream(file, true);
            //2.写数据到文件
            s = "hello";//假设这个s的值会通过外部传入
            fos.write(s.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException | NullPointerException e) {
            // System.out.println("文件没找到:"+e.getMessage());
            e.printStackTrace();//打印异常栈信息(包含的异常信息会更全面)
            //return;//遇到return语句时，是先finally，然后再返回
            //System.exit(-1);这条语句执行时，finally不在执行
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (fos != null) {
                fos.close();
                System.out.println("close fos successfully ! ");
            } else System.out.println("fos was null ! ");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
