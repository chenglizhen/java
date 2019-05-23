package dataStructure;

/**
 * @author cheng
 * 快速排序算法
 */
public class QuickSort{
	
	/**
	 * @param arr 要排序的数组
	 * @param low 
	 * @param high
	 */
	public void quickSort(int arr[],int low,int high) {
		int start = low;
		int end = high;
		int key = arr[low];
		
		while (start < end) {
			while (start < end && key <= arr[end]) {
				end--;
			}
			if (key >= arr[end]) {
				int tmp = arr[end];
				arr[end] = arr[start];
				arr[start] = tmp;
			}
			while (start < end && key >= arr[start]) {
				start++;
			}
			if(key <= arr[start]) {
				int tmp = arr[start];
				arr[start] = arr[end];
				arr[end] = tmp;
			}
		}
		if (start > low ) {
			quickSort(arr,low,start-1);
		}
		if(end < high) {
			quickSort(arr,end+1,high);
		}
	}
	public static void main (String args[]) {
		int arr[] = {12,20,5,16,15,1,30,45,23,9};
		int start = 0;
		int end  = arr.length -1;
		new QuickSort().quickSort(arr,start,end);
		for(int a:arr) {
			System.out.print(a + "	" );
		}
	}
}