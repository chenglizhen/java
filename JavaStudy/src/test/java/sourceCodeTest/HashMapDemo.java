package sourceCodeTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * @ClassName HashMapDemo
 * @Description 
 * @Author Cheng Lizhen
 * @Date 2019年3月15日 上午11:55:54
 */
public class HashMapDemo {
	
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String,String>();
		// 键不能重复，值可以重复
		map.put("san", "张三");
		map.put("si", "李四");
		map.put("wu", "王五");
		map.put("wang", "老王"); // 老王被覆盖
		map.put("wang", "老王2");
		System.out.println("----------直接输出HashMap---------");
		System.out.println(map);
		
		/**
		 *  遍历HashMap 
		 */
		// 1. 获取Map中的所有键
		System.out.println("--------foreach获取Map中的所有键-------");
		Set<String> keys = map.keySet();
		for(String key : keys) {
			System.out.print(key + "	");
		}
		System.out.println();
		
		// 2.获取Map中所有值
		System.out.println("--------foreach获取Map中的所有值---------");
		Collection<String> values = map.values();
		for(String v : values) {
			System.out.print(v + "	");
		}
		System.out.println();
		
		// 3.得到key的值的同时得到key所对应的值
		System.out.println("-------得到key的值的同时得到key所对应的值:-------");
		Set<String> keys2 = map.keySet();
		for(String key : keys2) {
			String value = map.get(key);
			System.out.print(key + ":" + value + "	");
		}
		System.out.println();
		
		/**
		 * HashMap 其他常用方法 
		 */
		System.out.println(map.size());
		System.out.println(map.isEmpty());
		System.out.println(map.remove("wang"));
		System.out.println(map);
		System.out.println(map.get("si"));
		System.out.println(map.containsKey("si"));
		System.out.println(map.containsValue("李四"));
		System.out.println(map.replace("si", "李四2"));
		System.out.println(map);
		
	}
}
