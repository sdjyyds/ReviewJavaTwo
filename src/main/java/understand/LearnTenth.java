package understand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 *
 * Map映射集
 * 原理：
 * 存储键值类型的数据结构,底层原理为Hash分桶算法.
 * 特点:
 * 键不能重复,值可以重复,存储时顺序信息会丢失,存取效率高,但是对存储空间有一定的浪费
 *
 * API
 * V put(K Key,V v),V get(Object key),int size(),V remove(Object key)
 * boolean containsKey(Object key),boolean containsValue(Object value)
 * boolean isEmpty(),void clear()
 *
 *
 */
public class LearnTenth {
}
class Demo02 {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("数学",99);
        map.put("语文",87);
        map.put("英语",77);
        map.put("体育",66);
//遍历1：获取所有键的集合,遍历出每一个键,通过键查找值
        Set<String> keys = map.keySet();
        for(String key : keys){
            Integer value = map.get(key);
            System.out.println(key + "#" + value);
        }
//遍历2:获取所有键值对的集合,从键值对中获取键值
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for(Map.Entry<String,Integer> entry : entries){
            System.out.println(entry.getKey()+"#"+entry.getValue());
        }
//遍历3:直接对值进行遍历
        Collection<Integer> values = map.values();
        for(Integer v : values){
            System.out.println(v);
        }
//遍历4:使用forEach方法,通过lambda表达式遍历
        map.forEach((k,v)-> System.out.println(k+"#"+v));
    }
}