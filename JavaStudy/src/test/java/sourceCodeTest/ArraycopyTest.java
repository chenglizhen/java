/**
 * 
 */
package sourceCodeTest;

/**
 * @ClassName ArraycopyTest
 * @Description  System.arraycopy 方法测试， Arrays.copyOf 方法进行对比
 * @Author Cheng Lizhen
 * @Date 2019年3月15日 上午11:52:05
 */
public class ArraycopyTest {
	public static void main(String[] args) {
		int[] a = new int[10];
		a[0] = 0;
		a[1] = 1;
		a[2] = 2;
		a[3] = 3;
		System.arraycopy(a, 2, a, 3, 3);
		a[2] = 99;
		for(int aa : a) {
			System.out.print(aa + "  ");
		}
	}
}
