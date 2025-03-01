package exercise.chat;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class SimpleChatTwo {
    public static void main(String[] args) {
        ServerTwo server = new ServerTwo();
        server.setServerSocket("127.0.0.1", 10002);
        System.out.println("server: " + server + " 构建成功！");
        server.start();
    }
}

/**
 * 实现服务端的功能，接收客户端对象，并且将对象存储在某个指定的集合中。
 */
class ServerTwo {
    private ServerSocket serverSocket;

    public void setServerSocket(String ip, int port) {
        InetSocketAddress isa = new InetSocketAddress(ip, port);
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(isa);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void start() {
        Socket client = null;
        //监听请求
        System.out.println("开启监听…………");
        try {
            while (true) {
                System.out.println("持续监听中…………");
                //1.获取客户端对象
                client = serverSocket.accept();
                System.out.println("获取对象成功！");
                //2.对客户端对象进行响应(读写响应)
                ManageClients.createClientThread(client);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

/**
 * MyThread
 */
class MyThread extends Thread {
    private Socket client;
    private BufferedWriter bw;

    public void run() {
        ServerActionImplTwo.HandleData(client);
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }
}

/**
 * 维护集合，构建客户线程
 */
abstract class ManageClients {
    private ManageClients() {
    }

    //用来映射对应的线程，当用户断开连接可以第一时间切断联系，避免资源浪费，也可以实现消息的传递
    public static final HashMap<Socket, MyThread> CLIENTS = new HashMap<>();

    /**
     * 构建并运行客户端线程,并加入集合
     */
    public static void createClientThread(Socket client) {

        synchronized (CLIENTS) {
            //创建用户线程和用户的写对象(方便进行写入操作)
            MyThread myThread = null;
            BufferedWriter bw = null;
            //加入输出集 -- 方便看到所有人的讯息
            try {
                bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println(e);
            }
            myThread = new MyThread();
            myThread.setBw(bw);
            myThread.setClient(client);
            //加入映射集
            CLIENTS.put(client, myThread);
            System.out.println("创建用户线程成功！");
            //开启线程
            myThread.start();
        }
    }

    public static void remove(Socket client) {
        CLIENTS.remove(client);
    }

}

/**
 * 动作实现类，接收数据，传递数据，释放客户端对象资源
 */
abstract class ServerActionImplTwo {
    private ServerActionImplTwo() {
    }

    public static void HandleData(Socket client) {
        String str = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            bw = ManageClients.CLIENTS.get(client).getBw();
            //list.remove(bw);
            System.out.println("收到来自：" + client + "的数据，准备转发…………");
            while (true) {
                {
                    //System.out.println("剩下用户线程的个数："+ManageClients.CLIENTS.size());
                    //System.out.println(client);
                    str = br.readLine();
                    //System.out.println(str);
                    if (str != null) {
                        if (str.equals("exit")) {
                            bw.write("exit");
                            bw.newLine();
                            bw.flush();
                            System.out.println("删除前集合大小：" + ManageClients.CLIENTS.size());
                            //todo:待实现
                            ManageClients.remove(client);
                            System.out.println("删除client线程成功！");
                            System.out.println("删除后集合大小：" + ManageClients.CLIENTS.size());
                            break;
                        }
                        sendData(client, str);
                        System.out.println("发送数据成功！");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("释放资源");
        }
    }

    public static synchronized void sendData(Socket currentclient, String str) {
        ManageClients.CLIENTS.forEach((client, thread) -> {
            if (client != currentclient) {
                BufferedWriter bw = thread.getBw();
                try {
                    System.out.println(bw);
                    bw.write(str);
                    bw.newLine();
                    bw.flush();
                    System.out.println(str);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
    }
}

/**
 * 控制输入和输出的线程，由于输入采用的是控制台(堵塞),输出采用的是socket,所以得需要要两个线程完成整个任务
 */
class ClientTwo {

    public static void main(String[] args) {
        Socket client = new Socket();
        BufferedReader br = null;
        BufferedWriter bw = null;
        ClientActionImplTwo cait = null;
        try {
            client.connect(new InetSocketAddress("127.0.0.1", 10002));
            cait = new ClientActionImplTwo();
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
            br = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            Thread readThread = new Thread(new ReadRunnableTwo(br, cait));
            readThread.start();
            System.out.println("开启读线程成功");
            Thread writeThread = new Thread(new WriteRunnableTwo(bw, cait));
            writeThread.start();
            System.out.println("开启写线程成功！");
            readThread.join();
            writeThread.join();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        } finally {
            try {
                if (br != null) br.close();
                if (bw != null) bw.close();
                if (client != null) client.close();
                System.out.println("finish ! ");
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}

/**
 * 读线程对象
 */
class ReadRunnableTwo implements Runnable {
    private BufferedReader br;
    private ClientActionImplTwo cait;

    public ReadRunnableTwo(BufferedReader br, ClientActionImplTwo cait) {
        this.br = br;
        this.cait = cait;
    }

    public void run() {
        cait.acceptData(br);
    }
}

/**
 * 写线程对象
 */
class WriteRunnableTwo implements Runnable {
    private BufferedWriter bw;
    private ClientActionImplTwo cait;

    public WriteRunnableTwo(BufferedWriter bw, ClientActionImplTwo cait) {
        this.bw = bw;
        this.cait = cait;
    }

    public void run() {
        cait.writeData(bw);
    }
}

/**
 * 该类负责动作实现：输入和输出
 */
class ClientActionImplTwo {
    public void writeData(BufferedWriter bw) {
        String line = null;
        Scanner scanner = new Scanner(System.in);
        try {
            while (!(line = scanner.next()).equals("exit")) {
                bw.write(line);
                bw.newLine();
                bw.flush();
                //System.out.println(line);
            }
            bw.write("exit");
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void acceptData(BufferedReader br) {
        String line = null;
        try {
            while (true) {
                if ((line = br.readLine()).equals("exit")) {
                    System.out.println("exit");
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
