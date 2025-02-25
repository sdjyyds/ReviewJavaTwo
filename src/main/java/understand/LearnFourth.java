package understand;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * translation between Collection and Array
 * Collection提供了一个接口toArray() -- convert collection to array
 * Arrays.asList() -- convert array to collection
 * <p>
 * sorting of collection
 * collections是集合的工具类
 * Collections.sort(param), Arrays.sort(param)(数组的排序) -- 都重载,可传入comparator--functional interface
 * list.sort(comparator)is equivalent to Collections.sort(list, comparator)
 * 排序内置的类型直接可排 -- 类型实现了comparable接口
 * 自定义元素排序得实现comparable接口或者传入comparator(该方式可以降低系统的耦合度)
 *
 * java.io.File class
 * An abstract representation of file and directory pathNames.
 * role:
 * 1.访问其表示的文件或目录的属性信息,例如:名字,大小,修改时间等等
 * 2.创建和删除文件或目录
 * 3.访问一个目录中的子项
 * 不能访问文件的数据
 */
public class LearnFourth {
    @Test
    public void testFile() throws IOException {
        File file =  new File("./src/main/g");
        file.isFile();
        file.isDirectory();
        file.mkdir();
        System.out.println(file.isFile());
        System.out.println(file.exists());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());
        //只能删除空目录或者一个文件
        file.delete();
        //可以级联创建目录 mkdirs
        //报错 --找不到指定的路径
//        file = new File("./src/main/a/b/a/b/1.txt");
//        file.createNewFile();
        file = new File("./src/main/a/b/a/b");
        file.mkdirs();
        //返回目录的大小 -- 无法返回(不支持)
        //只能返回文件的大小
        file = new File("./.idea");
        System.out.println(file.getCanonicalPath());
        System.out.println(file.length());
        file = new File("./src/main/a/b/a/b/1.txt");
        file.createNewFile();
        System.out.println(file.exists());
        //返回文件的大小(单位字节--类型long)
        System.out.println(file.length());
    }

    @Test
    public void testCollectionSort() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(100));
        }
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        list.sort((a, b) -> b - a);
        System.out.println(list);
        List<Point> points = new ArrayList<>();
        points.add(new Point(1.01, 1.0));
        points.add(new Point(1.02, 1.0));
        points.add(new Point(3, 3));
        points.add(new Point(2, 2));
        points.add(new Point(4, 4));
        points.add(new Point(5, 5));
        System.out.println(points);
        /*
        这样写这个函数会有侵入性，得将实现改写到lambda表达式中(无侵入性)
        points.sort((a, b) -> Double.compare(a.distanceTo(b), 0.0));
         */
        points.sort((Point p1, Point p2) -> {
            double value = Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2) - Math.pow(p2.getX(), 2) - Math.pow(p2.getY(), 2);
            return Double.compare(value, 0);
        });
        System.out.println(points);
    }

    @Test
    public void testTranslationOne() {
        //自动装箱
        Double[] doubles = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        //1.只打印地址
        //[Ljava.lang.Double;@365185bd
        //[--数组,L--object类型或其子类,java.lang.Double--类型;--类型描述结束
        //@365185bd--内存地址哈希值
        //debug检测下Double[10]@内存地址(真)
        System.out.println(doubles);
        //2.打印值
        System.out.println(Arrays.toString(doubles));
        //3.数组转化为集合
        /*
        数组的工具类Arrays提供了一个静态的方法asList，可以将数组转换为一个List集合
         */
        List list = Arrays.asList(doubles);
        System.out.println(list);
        list.set(1, 100.0);
        //数组也跟着变了,由此可知集合只是引用了数组原来的那一片空间
        System.out.println(Arrays.toString(doubles));
        /*4.集合转化为数组
        重载的toArray方法要求传入一个数组，内部会将集合所有元素存入该数组将其返回
(前提是该数组长度>=集合的size),如果给定的数组长度不足,则会自建一个与集合长度相等的数组
        并存入集合元素将其返回
        */
        Double[] copyDoubles = (Double[]) list.toArray(new Double[1]);
        System.out.println(Arrays.toString(copyDoubles));
        //改变数组的值,集合并未发生改变,因为本来就是生成一个新的数组空间,将集合中的元素放入
        copyDoubles[1] = 2000.1;
        System.out.println(list);
        /*
        所有的集合都支持一个参数为Collection的构造方法，作用是在创建当前
        集合的同时包含给定集合中的所有元素
        */
        List<String> list2 = new ArrayList<>(list);
        System.out.println("list2:" + list2);
        list2.add("seven");
        System.out.println("list2:" + list2);
    }
}
