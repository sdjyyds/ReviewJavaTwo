package exercise.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * <p>
 * Socket
 * java中用于网络通讯的一个对象,用于实现客户端与服务器的通讯
 * 需求:客户端向服务端写一句话,服务端进行输出.
 * 实现步骤:
 * 编写服务器类Server (创建服务对象,启动服务,接收客户端的连接,处理连接请求)
 * 编写客户端类Client (创建客户端对象,建立连接,向服务器发送请求)
 * 运行Server和Client对象(要注意先后顺序,先启动Server,再启动Client)
 * 简单单服务器对单客户端的对话已实现，现在需要在已有的基础上进行功能的扩展
 * 功能改进之聊天室的实现：服务器实现消息的传递功能，不再单独是个单纯的对话功能了
 * 代码实现请见SimpleChatTwo
 */
public class Server {
    private ServerSocket serverSocket;
    public Server() {
    }
    public void setServerSocket(String... IPAndPort) {
        InetSocketAddress isa = new InetSocketAddress(IPAndPort[0], Integer.parseInt(IPAndPort[1]));
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(isa);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void startServer() {
        Socket client = null;
        //监听请求
        try {
            while (true) {
                //1.获取客户端对象
                client = serverSocket.accept();
                System.out.println("获取对象成功！");
                //2.对客户端对象进行响应(读写响应)
                handleServer(client);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 封装服务器端对客户端的响应
     * @param client:指定客户端
     */
    public void handleServer(Socket client) {
        //开启读线程
        ReadRunnable rr = new ReadRunnable();
        try {
            rr.setBr(new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        new Thread(rr).start();
        System.out.println("读线程启动！");
        //开启写线程
        WriteRunnable wr = new WriteRunnable();
        try {
            wr.setPw(new PrintWriter(client.getOutputStream(), true));
        } catch (IOException e) {
            System.out.println(e);
        }
        new Thread(wr).start();
        System.out.println("写线程启动！");
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.setServerSocket("127.0.0.1", "9999");
        server.startServer();
    }
}

class WriteRunnable implements Runnable {
    PrintWriter pw;

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public void run() {
        writeData(pw);
    }

    public void writeData(PrintWriter pw) {
        String line = null;
        Scanner scan = new Scanner(System.in);
        while (!(line = scan.nextLine()).equals("exit")) {
            try {
//                System.out.println(line);
                pw.println(line);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        pw.println("exit");
    }
}

class ReadRunnable implements Runnable {
    private BufferedReader br = null;

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    @Override
    public void run() {
        readData(br);
    }

    public void readData(BufferedReader br) {
        //读取数据
        String str = null;
        try {
            do {
                str = br.readLine();
                //打印出来
                System.out.println(str);
            } while (!str.equals("exit"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class Client {
    public static void main(String[] args) {
        Socket client = null;
        ReadRunnable rr = null;
        WriteRunnable wr = null;
        Thread readThread, writeThread = null;
        try {
            //1.创建Socket对象
            client = new Socket();
            client.connect(new InetSocketAddress("127.0.0.1", 9999));
            //System.out.println(client);
            //2.检查Socket连接
            if (!client.isConnected()) {
                System.out.println("Socket未连接");
                return;
            }
            //4.开启读线程
            rr = new ReadRunnable();
            rr.setBr(new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)));
            readThread = new Thread(rr);
            readThread.start();
            System.out.println("读线程启动！");
            //5.开启写线程
            wr = new WriteRunnable();
            wr.setPw(new PrintWriter(client.getOutputStream(), true));
            writeThread = new Thread(wr);
            writeThread.start();
            System.out.println("写线程启动！");
            readThread.join();
            writeThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==finish==");
    }
}