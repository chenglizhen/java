package sourceCodeTest;

import java.util.ArrayList;

/**
 * @ClassName EnsureCapacityTest
 * @Description 测试使用EnsureCapacityTest方法前后，在list中插入大量数据的效率区别
 * @Author Cheng Lizhen
 * @Date 2019年3月15日 上午11:52:48
 */
public class EnsureCapacityTest {
	public static void main(String[] args) {
		ArrayList<Object> list = new ArrayList<Object>();
		final int N = 10000000;
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < N; i++) {
			list.add(i);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("使用ensureCapacity方法前："+(endTime - startTime));

		list = new ArrayList<Object>();
		long startTime1 = System.currentTimeMillis();
		list.ensureCapacity(N);
		for(int i = 0; i < N; i++) {
			list.add(i);
		}
		long endTime1 = System.currentTimeMillis();
		System.out.println("使用ensureCapacity方法后："+(endTime1 - startTime1));

		
	}
}
