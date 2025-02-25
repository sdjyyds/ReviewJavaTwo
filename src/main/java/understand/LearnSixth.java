package understand;

import org.junit.Test;
import tool.Scan;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * java将IO分为了两类
 * 节点流:又称为"低级流"
 * 特点:直接链接程序与另一端的"管道"，是真实读写数据的流
 * IO一定是建立在节点流的基础上进行的。
 * 文件流就是典型的节点流(低级流)
 * 处理流:又称为"高级流"
 * 特点:不能独立存在，必须链接在其他流上
 * 目的:当数据经过当前高级流时可以对数据进行某种加工操作，来简化我们的同等操作
 * 实际开发中我们经常"串联"一组高级流最终到某个低级流上，使读写数据以流水线式的加工处
 * 理完成。这一操作也被称为使"流的链接"。流链接也是JAVA IO的精髓所在。
 * 缓冲流
 * java.io.BufferedInputStream和BufferedOutputStream
 * 功能
 * 在流链接中的作用:加快读写效率
 * 通常缓冲是最终链接在低级流上的流
 * BufferedOutputStream(OutputStream out),BufferedOutputStream(OutputStream out,int size)
 * BufferedInputStream(InputStream in),BufferedInputStream(InputStream in,int size)
 * 缓冲流在读写数据时总是以块读写数据(默认是8kb)来保证读写效率的,缓冲流提供了多种构造器，可以自行指定缓冲区大小。
 * 由于缓冲输出流会将写出的数据装满内部缓冲区(默认8kb的字节数组)后才会进行一次真实的写出操作。
 * 当我们的数据不足时，如果想要及时写出数据，可以调用缓冲流的flush()方法，强制将缓冲区中已经缓存的数据写出一次。
 * 对象流
 * java.io.OutputObjectStream,java.io.InputObjectStream
 * 对象输出流:将我们的java对象进行序列化(将一个对象转换一组可被传输或保存的字节)
 * 对象输入流:将java对象进行反序列化(逆过程)todo:要想使用反序列化,必须先序列化,而且不能对存入机制不是序列化存入的请求会拒绝
 * 序列化的意义：
 * 写入磁盘,进行长久保存
 * 在网络间的两台计算机的java间进行传输
 * 无论是保存在磁盘中还是传输，都需要将对象转换为字节后才可以进行
 * void writeObject(Object obj) -- 将给定的对象转化为一组可保存或传输的字节然后通过其连接的流将字节写出
 * void readObject() -- 将通过其连接的流读取的字节转化为对象
 * 读取序列化数据中的类名：序列化数据中包含了被序列化对象的类名，例如 Person。
 * 1.查找类加载器：Java 会使用当前线程的类加载器（ClassLoader）来加载对应的类。
 * 如果当前线程没有类加载器，则使用系统类加载器（SystemClassLoader）。
 * 2.加载类：类加载器会根据类名查找对应的类文件（.class文件）。如果类文件存在，则加载类。
 * 3.检查 SerialVersionUID：如果类加载成功，Java 会检查序列化数据中的 SerialVersionUID
 * 是否与当前类的 SerialVersionUID 匹配。如果不匹配，Java 就会抛出 InvalidClassException。
 * 4.创建对象：如果 SerialVersionUID 匹配，Java 会创建一个新的对象实例，类型为被序列化对象的类（例如 Person）。
 * 5.反序列化对象：Java 会将序列化数据中的字段值反序列化到新创建的对象实例中。(被修饰的属性就会为null或默认值)
 * transient 修饰的成员变量不会被序列化,忽略不必要的属性达到对象"瘦身"操作,如果该对象不要序列化，那么该关键字发挥不了任何效果
 * <p>
 * 字符流
 * java将流按照读写单位划分为字节流与字符流.
 * java.io.InputStream和OutputStream是所有字节流的超类
 * 而java.io.Reader和Writer则是所有字符流的超类,它们和字节流的超类是平级关系.
 * Reader和Writer是两个抽象类,里面规定了所有字符流都必须具备的读写字符的相关方法.
 * 字符流最小读写单位为字符(char),但是底层实际还是读写字节,只是字符与字节的转换工作由字符流完成.字符流都是高级流
 * <p>
 * java.io.writer
 * void write(int c):写出一个字符,写出给定int值”低16”位表示的字符。
 * void write(char[] chs):将给定字符数组中所有字符写出。
 * void write(String str):将给定的字符串写出
 * void write(char[] chs,int offset,int len):将给定的字符数组中从offset处开始连续的
 * <p>
 * java.io.Reader
 * int read():读取一个字符，返回的int值“低16”位有效。当返回值为-1时表达流读取到了末尾。
 * int read(char[] chs):从该流中读取一个字符数组的length个字符并存入该数组，返回值为实际
 * 读取到的字符量。当返回值为-1时表达流读取到了末尾。
 * <p>
 * 转换流
 * java.io.InputStreamReader和OutputStreamWriter是常用的字符流的实现类。
 * 实际开发中我们不会直接操作他们，但是他们在流连接中是必不可少的一环。
 * 流连接中的作用
 * 衔接字节流与其他字符流
 * 将字符与字节相互转换
 * 意义
 * 实际开发中我们还有功能更好用的字符高级流.但是其他的字符高级流都有一个共通点:不能直接连接在字
 * 节流上.而实际操作设备的流都是低级流同时也都是字节流.因此不能直接在流连接中串联起来.转换流是
 * 一对可以连接在字节流上的字符流,其他的高级字符流可以连接在转换流上.在流连接中起到"转换器"的作
 * 用(负责字符与字节的实际转换)
 * OutputStreamWriter(OutputStream out,Charset cs),OutputStreamWriter(OutputStream out)
 * InputStreamWriter(InputStream in,Charset cs),InputStreamWriter(InputStream in)
 * <p>
 * 缓冲字符流
 * 缓冲字符输出流-java.io.PrintWriter
 * java.io.BufferedWriter和BufferedReader
 * 缓冲字符流内部也有一个缓冲区,读写文本数据以块读写形式加快效率.并且缓冲流有一个特别的功
 * 能:可以按行读写文本数据.缓冲流内部维护一个char数组，默认长度8192.以块读写方式读写字符数据保证效率
 * java.io.PrintWriter具有自动行刷新的缓冲字符输出流,实际开发中更常用.它内部总是会自动连
 * BufferedWriter作为块写加速使用.
 * PrintWriter(File file),PrintWriter(String path)
 * PrintWriter(File file,String csn),PrintWriter(String path,String csn)
 * 上述构造器看似PW可以直接对文件进行操作，但是它是一个高级流，实际内部会进行流连接:
 * this(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName))),false);
 */

public class LearnSixth {
    @Test
    public void testPW() throws FileNotFoundException {
        //负责:将写出的字节写入到文件中
        FileOutputStream fos = new FileOutputStream("./src/main/pw2.txt");
//负责:将写出的字符全部转换为字节(可以按照指定的字符集转换)
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//负责:块写文本数据(攒够8192个字符一次性写出)
        BufferedWriter bw = new BufferedWriter(osw);
//负责:按行写出字符串
        PrintWriter pw = new PrintWriter(bw);
        pw.println("你停在了这条我们熟悉的街,");
        pw.println("把你准备好的台词全念一遍。");
        System.out.println("写出完毕");
        pw.close();
    }

    @Test
    public void testObjectStream() throws IOException, ClassNotFoundException {
        File file = new File("./src/person.obj");
        file.createNewFile();
        Person person = new Person("jds", 18);
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        oos.writeObject(person);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        //System.out.println(ois.readUTF());--EOFException
        System.out.println(ois.readObject());
    }
}

class Person implements Serializable {
    public final static long serialVersionUID = 1L;
    private String name;
    private transient int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

/**
 * 实现一个简易的记事本工具
 * 利用流连接
 * 在文件输出流上最终链接到PrintWriter上。
 * 然后将用户在控制台上输入的每一行字符串都可以按行写入到对应的文件中。
 * 当用户在控制台上单独输入"exit"时程序退出。
 */
class NotePad {
    public static void main(String[] args) {
        NotePad notePad = new NotePad();
        notePad.run();
    }
    public void run() {
        String mode = null;
        do {
            System.out.println("请输入你想要进入的模式：1.读,2.写,3.退出");
            mode = Scan.getScanner().nextLine();
            switch (mode) {
                case "1":
                   // System.out.println("****");
                    read();
                    break;
                case "2":
                    write();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("sorry,没有这个模式");
            }
        } while (!mode.equals("3"));
    }
//采用ByteBuffer来读
    void read() {
        Scanner scanner = Scan.getScanner();
        FileInputStream fis = null;
        ByteBuffer byteBuffer = null;
        FileChannel fileChannel = null;
        try {
            fis = new FileInputStream("./src/main/pw.txt");
            //为什么使用fileChannel和byteBuffer
            //(为了提高读写性能,这两个类都是底层类,直接使用它们比借助高级流更具有性能优势)
            fileChannel = fis.getChannel();
            byteBuffer = ByteBuffer.allocate(1024);
            while((fileChannel.read(byteBuffer)) != -1)
            {
                //切换为写模式
                byteBuffer.flip();
                //写入到控制台
                System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit(),StandardCharsets.UTF_8));
                //清除字节序列，为下一次读操作做准备
                byteBuffer.clear();
            }
            fis.close();
            fileChannel.close();
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void write() {
        Scanner scanner = Scan.getScanner();
        String line = null;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("./src/main/pw.txt"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("请输入内容，输入exit退出! ");
        while (!(line = scanner.nextLine()).equals("exit")) {
            pw.println(line);
        }
        pw.close();
    }
}