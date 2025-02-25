package understand;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * collection framework
 * java.util.Collection is the root interface of all collection framework
 * collection 下常见的子接口 --> List, Set, Queue
 * 集合只能存放引用类元素，并且存放的是元素的引用
 * 集合遍历方式：
 * 1.迭代器
 * 2.增强for -- 本质还是迭代器 编译器会把增强for转换成迭代器,新循环是java编译器认可的，并非虚拟机。
 * 3.for-each -- jdk5后推出，对集合用本质是迭代对数组用本质还是for循环
 * 但是编译器不会把for-each转换成迭代器，本质是迭代器是说底层代码逻辑实现是通过迭代器的
 * java.util.iterator interface
 * 定义了迭代器遍历集合的相关操作
 * 不同的集合都实现了一个用于遍历自身元素的迭代器实现类
 * 无需记住他们的名字，用多态的角度把他们看作为iterator即可
 * 迭代器遍历集合的步骤为: 问,取,删，其中删除元素不是必要操作
 * 迭代器遍历过程中不得通过集合的方法增删元素
 * <p>
 * 泛型(generics programming)
 * jdk5后推出另一个特性：泛型
 * 泛型也称为参数化类型,允许我们在使用一个类时指定它当中属性,方法参数或返回值的类型.
 * 泛型在集合中被广泛使用,用来指定集合中的元素类型.
 * 有泛型支持的类在使用时若不指定泛型的具体类型则默认为原型Object
 * 要表达既是这个又是那个可以使用泛型
 * <p>
 * java.util.List interface extends Collection
 * List集合是可重复有序集合，提供了一套可以通过下标操作元素的方法
 * 常用实现类：ArrayList(内部数组实现，查询性能好), LinkedList(链表实现，首尾增删性能好)
 * todo:接口可以extends多个接口
 */
//我勒个泛型大爆炸之java硬刚programmer!
//注意泛型放的的位置
public class LearnThird<E> {
    @Test
    public void testList() {
        List<String> l = new ArrayList<>();
        l.add("one");
        l.add("two");
        l.add("three");
        l.add("four");
        l.add("five");
        l.sort((s1, s2) -> -s1.compareTo(s2));
        //l.sort((s1, s2) -> s2.compareTo(s1));
        //List集合是可重复有序集合，提供了一套可以通过下标操作元素的方法
        //list常见方法 get,set,add,remove,sort,subList(参数)
        System.out.println(l);
    }

    @Test
    public void testGP() {
        Collection<String> c = new ArrayList<>();
        c.add("one");//编译器会检查add方法的实参是否为String类型
        c.add("two");
        c.add("three");
        c.add("four");
        c.add("five");
        // c.add(123);//编译不通过
        System.out.println(c);
    }

    public static void main(String[] args) {
        LearnThird<String> lt = new LearnThird<>();
        // lt.testCollectionTwo(111); --报错
        lt.testCollectionTwo("sss");
    }

    //泛型位置
    //@Test --有了泛型不能直接测试了，因为运行该方法不知道E指的到底是什么.java禁止了
    public <G> void testCollectionTwo(E a) {
        //证明：集合只能存放引用类元素，且存放的是元素的引用
        Point p1 = new Point(10086, 10010);
        Collection c = new ArrayList();
        c.add(p1);
        System.out.println(c);
        p1.setX(110);
        //改变p1的值，集合中的元素也会跟着改变
        System.out.println(c);
        Collection<String> c1 = new HashSet();//不可重复元素
        c1.add("java");
        c1.add("c");
        c1.add("c++");
        System.out.println("c1:" + c1);
        Collection c2 = new ArrayList();
        c2.add("android");
        c2.add("ios");
        c2.add("java");
        System.out.println("c2:" + c2);
        /*
        boolean addAll(Collection c)
        将给定集合中的所有元素添加到当前集合中。当前集合若发生了改变则返回true
        */
        boolean tf = c1.addAll(c2);
        System.out.println(tf);
        System.out.println("c1:" + c1);
        System.out.println("c2:" + c2);
        //集合的遍历方式
        //1.迭代器
        Iterator<String> it1 = c1.iterator();
        while (it1.hasNext()) {
            System.out.println(it1.next());
            it1.remove();
            //迭代器中的remove方法可以将next方法获取的元素从集合中删除
            //不允许使用集合的方式增删元素--ConcurrentModificationException
            //c1.add("python");
        }
        //2.增强for
        for (String s : c1) {
            System.out.println(s);
        }
        //3.for-each -- lambda 表达式 -- 本质还是迭代器
        c1.forEach((s) -> System.out.println(s));
    }

    @Test
    public void testCollectionOne() {
        AbstractCollection ac = new AbstractCollection() {
            @Override
            public Iterator iterator() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };
        //不是abstract class
        HashSet hs = new HashSet();
        Collection c = new ArrayList();
        /*
        boolean add(E e)
        向当前集合中添加一个元素.当元素成功添加后返回true
        */
        c.add("one");
        c.add("two");
        c.add("three");
        c.add("four");
        c.add("five");
        System.out.println(c);
        c.clear();
        System.out.println(c);
        System.out.println(c.size());
        System.out.println(c.isEmpty());
        System.out.println(c.contains("two"));
        System.out.println(c.containsAll(Arrays.asList("two", "three", "four")));
        //test equals and hashCode
        Point p1 = new Point(1, 3);
        Point p2 = new Point(3, 1);
        System.out.println(p1.equals(p2));
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
}

class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public String toString() {
        return "x:" + x + ",y:" + y;
    }

    public double getValue() {
        return x * x + y * y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        Point o1 = (Point) o;
        return o1.getValue() == this.getValue();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
