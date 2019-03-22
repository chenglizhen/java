package sourceCodeTest;

import java.util.Arrays;

/**
 * @ClassName ArrayscopyOfTest
 * @Description Arrays.copyOf方法测试，与 System.arraycopy 方法进行对比
 * @Author Cheng Lizhen
 * @Date 2019年3月15日 上午11:52:32
 */
public class ArrayscopyOfTest {
	public static void main(String[] args) {
		int[] a = new int[3];
		a[0] = 0;
		a[1] = 1;
		a[2] = 2;
		int[] b = Arrays.copyOf(a, 10);
		System.out.println("b.length:" + b.length);
	}
}
