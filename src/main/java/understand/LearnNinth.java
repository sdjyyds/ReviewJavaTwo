package understand;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * multiple threads
 * 进程
 * 操作系统中运行着的程序就是一个进程，可以同时运行多个程序(多进程)
 * 多个进程通过抢夺CPU执行时间进行并发执行,由于CPU运行速度很快,看起来就向同时执行一样
 * 补充：
 * 是计算机中正在运行的一个程序实例,是操作系统分配资源和调度的基本单位,每个进程都有自己的独立地址空间
 * 内存,数据和系统资源。
 * 特点：
 * 独立性(各进程相互独立,每个京城都有自己独立的地址空间)
 * 资源分配(操作系统为每个进程分配CPU时间,内存,文件句柄等资源)
 * 进程间通信(IPC,Inter-Process Communication):由于进程之间相互隔离.因此需要特殊的方式通讯
 * 如管道,消息队列,共享内存等进行通信
 * 开销较大(创建或销毁一个进程比创建或销毁线程的开销更大,因为它实际更多的资源管理)
 * <p>
 * 线程
 * 是进程中的执行单元,是CPU调度的资本单位,一个进程至少有一个线程(主线程),也可以有多个线程(多线程)
 * 特点：
 * 共享资源(统一进程内的线程共享该进程的地址空间,内存,文件句柄等资源)
 * 独立调度(每个线程有自己的程序计数器PC,寄存器和栈),但他们共享进程的堆和方法区
 * 切换开销小(线程间的切换比进程间的切换更快,因为他们共享相同的内存空间,减少了数据复制的开销)
 * 通信方便(线程之间可以直接通过共享变量进行通信,而不需要使用IPC机制)
 * <p>
 * 线程创建方式
 * 方式1：直接通过Thread类创建或继承该类进行创建
 * 方式2：间接创建,实现Runnable接口,调用Thread(Runnable runnable)构建
 * 运行：threadName.start(); ---自动调用实现的run方法
 * <p>
 * 处理线程的方式
 * 1.方法：Thread类中常用方法getCurrentThread,(sleep(参数),join(),interrupt(),stop()
 * setPriority(),getName(),setName(),getThreadGroup(),setDaemon(),getId(),yield()
 * getDaemon(),getContextClassLoader(),setContextClassLoader()),getPriority()
 * isAlive(),isDaemon(),isInterrupted()
 * <p>
 * 线程之间的关系
 * 程序从main方法启动时，本身就是一个主线程,任意线程中都可以开启新的线程
 * 默认情况下,线程之间都是平等的,平等抢夺CPU执行,只有所有线程结束了程序才会结束
 * 线程优先级决定了线程抢夺CPU的概率高低,优先级范围1-10,默认为5,数值越大权重越高
 * 守护线程
 * 线程分为用户线程和守护线程,线程创建出来是都是用户线程,可以将用户线程配置为守护线程
 * 守护线程通常执行的是用户线程的辅助任务,当程序中剩余县城都是守护线程时,程序会直接结束
 * <p>
 * 线程并发安全问题
 * 多个线程并发访问同一个共享资源,由于线程抢夺CPU的随即特性,造成对共享资源的访问产生混乱
 * <p>
 * 同步机制
 * synchronized代码块
 * synchronized(锁对象){要执行的同步代码}
 * 原理:
 * 线程想要进入同步代码块,必须在锁对象上加锁
 * 而同一个锁对象同一时间内只能有一个线程加锁成功,线程走出同步代码块时,自动解锁
 * 其他线程才有机会竞争到锁,进入同步代码块,操作共享资源,从而实现同步
 * 此机制保证了同一时间内只能有一个线程操作共享资源,避免了多线程并发安全问题.
 * <p>
 * 锁对象的选择
 * 任何对象都可以作为锁对象使用,锁对象必须是同一个对象,才能互斥
 * 因此必须选择所有要互斥的线程都能看到的对象作为锁对象
 * 常见的锁对象:共享资源作为锁对象,this作为锁对象,类名.class作为锁对象
 * 锁的抢夺是随机,并不保证顺序,加锁时尽量只加有风险的代码,其它代码尽量不要加进去,减少性能损耗
 * <p>
 * syncronized方法
 * 基本结构 public synchronized void 方法名(){要执行的同步代码}
 * 原理
 * 如果整个方法都需要保证线程安全,则可以直接在方法上声明同步
 * 此时,任何线程想要进入这个方法,都需要先获取到锁,保证了线程安全
 * 默认锁对象
 * syncronized方法的锁对象:
 * 如果是普通方法,则使用this作为锁对象
 * 如果是静态方法,则使用当前类.class作为锁对象
 * <p>
 * 并发安全API
 * 有一些类在开发中使用的非常频繁,在多线程的场景下,几乎无法避免产生潜在的多线程安全问题,如果都
 * 使用Syncronized来解决,则程序中到处都是同步锁,开发困难.
 * 为了解决这种问题,Java专门提供了一些并发安全的API,让我们不使用同步代码块也能保证这些API的线程
 * 安全.
 * StringBuilder - StringBuffer
 * ArrayList - Collections.synchronizedList(list);
 * LinkedList - Collections.synchronizedList(list);
 * HashSet - Collections.synchronizedSet(set);
 * HashMap - Collections.synchronizedMap(map);
 * <p>
 * 线程之间的通信
 * 等待唤醒机制
 * 可以在Syncronized代码块中,在锁对象上使用如下方法控制线程的等待和唤醒
 * wait() - 让当前线程进入阻塞等待状态 - 线程挂起 不再抢夺CPU 并释放锁
 * notify() - 随机唤醒一个当前在锁对象上阻塞等待的线程 - 线程结束挂起 恢复对CPU的抢夺 但仍然
 * 需要竞争到锁才可以继续执行
 * notifyAll() - 唤醒所有在当前锁对象上阻塞等待的线程 - 线程结束挂起 恢复对CPU的抢夺 但仍然需
 * 要竞争到锁才可以继续执行
 */
public class LearnNinth {

}

class Demo03 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread t1 = Thread.currentThread();
                System.out.println(t1.getId() + "#" + t1.getName() + "..start...");
                // Thread.sleep(1000 * 3);
                System.out.println(t1.getId() + "#" + t1.getName() + "..stop..");

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread t1 = Thread.currentThread();
                System.out.println(t1.getId() + "#" + t1.getName() + "..start...");
                //Thread.sleep(1000 * 3);
                System.out.println(t1.getId() + "#" + t1.getName() + "..stop..");
            }
        }).start();
        Thread tx = Thread.currentThread();
        System.out.println(tx);
        System.out.println("主线程结束了..");
    }
}

class Demo04 {
    public static void main(String[] args) {
        test01();
        //test02();
        // test03();
    }

    /**
     * 守护线程
     */
    public static void test03() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("用户线程在执行..");
                    Thread.sleep(3000);
                    System.out.println("用户线程执行结束..");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        System.out.println("守护进程在执行..");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }

    /**
     * 测试alive
     */
    public static void test02() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread t1 = Thread.currentThread();
                    System.out.println(t1.getId() + "开始运行..");
                    Thread.sleep(3000);
                    System.out.println(t1.getId() + "运行结束..");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        while (true) {
            System.out.println("主线程检测到t1是否存活:" + t1.isAlive());
        }
    }

    /**
     * 测试Priority
     */
    public static void test01() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("t1");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("t2");
                }
            }
        });
        System.out.println(t1.getPriority());
        System.out.println(t2.getPriority());
        t1.setPriority(10);
        t2.setPriority(1);
        t1.start();
        t2.start();
    }
}

class Demo05 {
    private static String name = "小明";
    private static String gender = "男";

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if ("小明".equals(name)) {
                        name = "小花";
                        gender = "女";
                    } else {
                        name = "小明";
                        gender = "男";
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(name + "#" + gender);
                }
            }
        }).start();
    }
}

class Demo07 {
    private static String name = "小明";
    private static String gender = "男";

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("其它无关代码..");
                    synchronized (Demo07.class) {
                        if ("小明".equals(name)) {
                            name = "小花";
                            gender = "女";
                        } else {
                            name = "小明";
                            gender = "男";
                        }
                    }
                    System.out.println("其它无关代码..");
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (Demo07.class) {
                        System.out.println(name + "#" + gender);
                    }
                }
            }
        }).start();
    }
}

class Demo08 {
    public static void main(String[] args) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        //StringBuffer sb = new StringBuffer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000; i++) {
                        Thread.sleep(1);
                        sb.append("abc");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000; i++) {
                        Thread.sleep(1);
                        sb.append("def");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(5000);
        System.out.println(sb.length());
        System.out.println(sb);
        int idx = 0, len = 3;
        String str = null;
        while (idx + len <= sb.length() && ((str = sb.substring(idx, idx + len)) != null)) {
            idx += len;
            //System.out.println(idx);
            if (str.equals("abc") || str.equals("def")) continue;
            //System.out.println(idx - len);
            //System.out.println(str);
            System.out.println("发生了线程同步问题！");
            System.exit(-1);
        }
        System.out.println("没有发生线程同步问题");
    }
}

class Demo10 {
    private static String name = "小明";
    private static String gender = "男";

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        synchronized (Demo10.class) {
                            if ("小明".equals(name)) {
                                name = "小花";
                                gender = "女";
                            } else {
                                name = "小明";
                                gender = "男";
                            }
                            Demo10.class.notify();
                            Demo10.class.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (Demo10.class) {
                        try {
                            System.out.println(name + "#" + gender);
                            Demo10.class.notify();
                            Demo10.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}

class Demo11 {
    static int i = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (Demo11.class) {
                        Thread.sleep(2000);
                        System.out.println("弟弟买米回来了..");
                        i++;
                        Demo11.class.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (Demo11.class) {
                        Thread.sleep(3000);
                        System.out.println("姐姐买菜回来了..");
                        i++;
                        Demo11.class.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (Demo11.class) {
                        Thread.sleep(5000);
                        System.out.println("爸爸买锅回来了..");
                        i++;
                        Demo11.class.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        synchronized (Demo11.class) {
            try {
                while (i < 3) {
                    Demo11.class.wait();
                }
                System.out.println("妈妈开始做饭了~~~");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Demo12 {
    private static int count = 0;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("安琪拉加载完成..");
                    synchronized (Demo12.class) {
                        count++;
                        if (count < 3) {
                            Demo12.class.wait();
                        } else {
                            Demo12.class.notifyAll();
                        }
                        System.out.println("安琪拉开始游戏..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("李白加载完成..");
                    synchronized (Demo12.class) {
                        count++;
                        if (count < 3) {
                            Demo12.class.wait();
                        } else {
                            Demo12.class.notifyAll();
                        }
                        System.out.println("李白开始游戏..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("花木兰加载完成..");
                    synchronized (Demo12.class) {
                        count++;
                        if (count < 3) {
                            Demo12.class.wait();
                        } else {
                            Demo12.class.notifyAll();
                        }
                        System.out.println("花木兰开始游戏..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}