package understand;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * <p>
 * learn file continued
 * 访问一个项目中的所有子项(递归)
 * public File[] listFiles([FileFilter filter]);--重载方法,可传入过滤器(filter)
 * <p>
 * java IO(Input and output)
 * java将输入和输出比喻为"流"(Stream),是一种抽象概念,类似于生活中的水流,只不过这里流动的是字节
 * 我们理解他们是连接程序和另一端的"管道",用于获取或发数据到另一端
 * javaIO有字节流,字符流,对象流
 * java.io.File,java.io.InputStream java.io.OutputStream,java.io.Reader,java.io.Writer
 * java.io.InputObjectStream,java.io.OutputObjectStream
 * 其中字节流又称低级流,字符流和对象流又称高级流(采用的是装饰模式--设计模式)
 * java I/O是java应用程序与外部数据(文件和网络)之间的桥梁
 * <p>
 * introduce java.io.InputStream
 * 是所有字节输入流的超类，里面定义了所有字节输入流都必须具备的读取字节的方法
 * int read()
 * 读取一个字节，以int形式返回，该int值的”低八位”有效，若返回值为-1则表示EOF
 * int read(byte[] b)
 * 尝试最多读取给定数组的length个字节并存入该数组，返回值为实际读取到的字节量
 * <p>
 * introduce java.io.outputStream
 * 是所有字节输出流的超类，里面定义了所有字节输出流都必须具备的写出字节的方法
 * void write(int d)
 * 写出一个字节,写的是给定的int的”低八位”
 * void write(byte[] data)
 * 将给定的字节数组中的所有字节全部写出
 * void write(byte[] data,int offset,int len)
 * 将给定的字节数组中从offset处开始的连续len个字节写出
 * <p>
 * 文件流
 * 文件流是用来链接我们的程序与文件之间的"管道",用来读写文件数据的流。
 * 文件流分为java.io.FileInputStream，java.io.FileOutputStream
 * 文件流是继承自InputStream和OutputStream
 */
public class LearnFifth {
    @Test
    public void testFileCopy() throws IOException {
        //1.使用字节流复制
        //1.1FileInputStream and FileOutputStream
        //    FileInputStream fis = new FileInputStream("./src/main/1.txt");
        new File("./src/main/2.txt").createNewFile();
//        FileOutputStream fos = new FileOutputStream("./src/main/2.txt");
//        byte [] buffer = new byte[1024];
//        int len = -1;
//        while((len = fis.read(buffer)) != -1){
//            fos.write(buffer,0,len);
//        }
//        fis.close();
//        fos.close();
        //1.2BufferedInputStream and BufferedOutputStream
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("./src/main/1.txt"));
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("./src/main/2.txt"));
//        byte[] buffer = new byte[1024 * 8];
//        int len = -1;
//        while((len = bis.read(buffer)) != -1){
//            bos.write(buffer,0,len);
//        }
//        bos.flush();
//        bis.close();
//        //不关闭的话导致的问题:1.数据泄漏,2.放在缓冲区没有flash(close时自动调用flush())数据无法导入(剩下8192或以下(默认))
//        bos.close();
        //2.使用字符流复制
        //2.1InputStreamReader and OutputStreamWriter
//        InputStreamReader isr = new InputStreamReader(new FileInputStream("./src/main/1.txt"),StandardCharsets.UTF_8);
//        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("./src/main/2.txt"),StandardCharsets.UTF_8);
//        CharBuffer cbf = CharBuffer.allocate(1024);
//        //CharBuffer cbb = CharBuffer.allocate(1024);
//        while(isr.read(cbf) != -1){
//            cbf.flip();//改为读模式
//            osw.write(cbf.array(),0,cbf.limit());//写入,cbf.limit()是有效数据的个数
//            cbf.clear();//清除缓存,为下一次读入做准备
//        }
//        isr.close();
//        osw.close();
//        2.2BufferedReader and BufferedWriter
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./src/main/1.txt"), StandardCharsets.UTF_8));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./src/main/2.txt"), StandardCharsets.UTF_8));
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            bw.write(line);
//        }
//        bw.write("\nwdf");
//        bw.flush();
//        br.close();
//        bw.close();
        //2.3,BufferedReader and PrintWriter
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./src/main/1.txt"), StandardCharsets.UTF_8));
//        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("./src/main/2.txt"), StandardCharsets.UTF_8));
//        String line = null;
//        while ((line = br.readLine()) != null) {
//            pw.println(line);
//        }
//        pw.flush();
//        br.close();
//        pw.close();
        //3.使用对象流复制
        //3.1ObjectInputStream and ObjectOutputStream
        //报错：如果你只是想读取一个文本文件，你应该使用FileReader或BufferedReader等类，而不是ObjectInputStream。
        // 如果你确实需要使用ObjectInputStream，那么你需要确保写入文件的代码使用ObjectOutputStream写入文件。
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./src/main/1.txt"));
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./src/main/2.txt"));
//        oos.writeUTF(ois.readUTF());
    }

    @Test
    public void testFileStreamIO() throws IOException {
        //默认其实就是借助File对象完成这件事情的
        //java.io.FileNotFoundException: ./src/main/1.txt
        // FileInputStream fis = new FileInputStream("./src/main/1.txt");
        //FileInputStream fis = new FileInputStream(new File("./src/main/1.txt"));
        File file = new File("./src/main/1.txt");
        file.createNewFile();
        FileInputStream fis = new FileInputStream("./src/main/1.txt");
        //System.out.println(fis.read());//-1
        FileOutputStream fos = new FileOutputStream("./src/main/1.txt");
        fos.write("喜羊羊,美羊羊,灰太狼,别看我只是一只羊,聪明的难以想象 ! ".getBytes(StandardCharsets.UTF_8));
        //方法1
//        byte[] chs = new byte[(int)file.length()];--只适合2^31-1字节之内的文件读取
//        fis.read(chs);
//        System.out.println(new String(chs,StandardCharsets.UTF_8));
        //方法2 --单个读取字节并且进行
//        byte[] chs = new byte[(int)file.length()];
//        byte b = -1;
//        int idx = 0;
//        while((b =(byte)fis.read()) != -1) {
//            chs[idx++] = b;
//        }
//        System.out.println(new String(chs,StandardCharsets.UTF_8));
        //解决如何读取大文件问题 -- 自我思考(采用字符流)
        //1.使用BufferInputStream
        //这种方案使用了BufferedInputStream，它会缓存一定量的数据（默认是8192字节）
        //这样可以减少磁盘I/O的次数，提高读取速度。
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("./src/main/1.txt"));
//        byte[] buffer = new byte[1024];
//        int len;
//        while ((len = bis.read(buffer)) != -1) {
//            // 处理读取到的数据
//            System.out.println(new String(buffer, 0, len, StandardCharsets.UTF_8));
//        }
//        bis.close();
        //2.使用FileChannel
        //这种方案使用了FileChannel，它可以直接操作文件的底层数据，避免了BufferedInputStream的缓存机制。
//        FileChannel channel = new FileInputStream("./src/main/1.txt").getChannel();
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        while (channel.read(buffer) != -1) {//使用文件通道对象进行读取,将读取到的数据存储在缓冲区中
//            buffer.flip();//切换到读取模式
//            // 处理读取到的数据
//            System.out.println(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));
//            buffer.clear();//清空缓冲区,切换到写模式
//        }
        //方案3：使用MappedByteBuffer todo:错误待解决
////        这种方案使用了MappedByteBuffer，它可以将文件直接映射到内存中，避免了磁盘I/O的开销。
//        FileChannel channel = new FileInputStream("./src/main/1.txt").getChannel();
//        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
//        while (buffer.hasRemaining()) {
//            // 处理读取到的数据
//            System.out.println(new String(buffer.array(), 0, buffer.remaining(), StandardCharsets.UTF_8));
//        }
//        channel.close();
//        channel.close();
        /*
        方案1：BufferedInputStream的缓存机制可以提高读取速度，但是缓存大小有限，可能需要多次读取才能读取完成整个文件。
方案2：FileChannel可以直接操作文件的底层数据，避免了缓存机制，但是需要手动管理缓冲区。
方案3：MappedByteBuffer可以将文件直接映射到内存中，避免了磁盘I/O的开销，但是需要足够大的内存空间来容纳整个文件。
         */
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        fos.close();
        fis.close();
    }

    @Test
    public void testInquireAllSubFiles() throws IOException {
        File rootFile = new File(".");
        printAllSubFiles(rootFile);
        printFixConditionSubFiles(rootFile, "(.class)$");
    }

    public void printFixConditionSubFiles(File file, String fixCondition) throws IOException {
        File[] subFile = getSubFiles(file);
        if (subFile != null) {
            for (File f : subFile) {
                if (f.getName().matches(fixCondition)) {
                    System.out.println(f.getCanonicalPath());
                }
            }
        }
    }

    //深搜
    public File[] getSubFiles(File file) {
        File[] files = null;
        if (file.exists()) {
            files = file.listFiles(f -> f.isFile());
            File[] dir = file.listFiles(f -> file.isDirectory());
            if (dir != null) {
                for (File f1 : dir) {
                    File[] subFiles = getSubFiles(f1);
                    if (subFiles != null) {
                        if (files == null) files = subFiles;
                        else {
                            files = Arrays.copyOf(files, files.length + subFiles.length);
                            System.arraycopy(subFiles, 0, files, files.length - subFiles.length, subFiles.length);
                        }
                    }
                }
            }
        }
        return files;
    }
//    public File[] getSubFiles(File file) {
//        File[] files = null;
//        if (file.exists()) {
//            files = file.listFiles(f -> f.isFile());
//            File[] dir = file.listFiles(f -> f.isDirectory());
//            if (dir != null) {
//                for (File f1 : dir) {
//                    File[] subFiles = getSubFiles(f1);
//                    if (subFiles != null) {
//                        if (files == null) files = subFiles;
//                        else {
//                            File[] newFiles = Arrays.copyOf(files, files.length + subFiles.length);
//                            System.arraycopy(subFiles, 0, newFiles, files.length, subFiles.length);
//                            files = newFiles;
//                        }
//                    }
//                }
//            }
//        }
//        return files;
//    }

    public void printAllSubFiles(File file) throws IOException {
        System.out.println(file.getCanonicalPath());
        //System.out.println(file.exists());
        if (file.exists()) {
            // System.out.println(1);
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    printAllSubFiles(subFile);
                } else {
                    System.out.println(subFile.getCanonicalPath());
                }
            }
        }
    }
}
