package dataStructure;

/**
 * @author cheng
 * 二分查找算法
 */
public class BinarySearch {

	/** 
	 * 循环实现二分查找算法
	 * @param arr 要进行查找的数组，要求数组必须是有序的
	 * @param findElem 要查找的元素
	 * @return 返回要查找的元素在数组的索引位置， 返回-1表示没找到
	 * 注意：计算中间位置时不应该使用(high+ low) / 2的方式，
	 * 因为加法运算可能导致整数越界，这里应该使用以下三种方式之一：
	 * low + (high - low) / 2或low + (high – low) >> 1或(low + high) >>> 1（>>>是逻辑右移，是不带符号位的右移）
	 */
	public static int loopBinarySearch(int[] arr,int findElem) {
		
		int low = 0;
		int high = arr.length - 1;
		int middle = 0;
		
		while (low <= high) {
			middle = low+(high-low)/2;
			if (findElem == arr[middle]) {
				return middle;
			} else if (findElem > arr[middle]) {
				low = middle + 1;
			} else {
				high = middle -1;
			}
		}
		return -1;
	}
	
    /**
     * 递归实现二分查找
     * @param dataset 要进行查找的数组，要求数组必须是有序的
     * @param data 要查找的元素
     * @param beginIndex 查找数组的起始位置
     * @param endIndex 查找数组的终止位置
     * @return
     */
    public static int recursionBinarySearch(int[] dataset,int data,int beginIndex,int endIndex){    
	   int midIndex = (beginIndex+endIndex)/2;    
	   if(data <dataset[beginIndex]||data>dataset[endIndex]||beginIndex>endIndex){  
		   return -1;    
	   }  
	   if(data <dataset[midIndex]){    
		   return recursionBinarySearch(dataset,data,beginIndex,midIndex-1);    
	   }else if(data>dataset[midIndex]){    
		   return recursionBinarySearch(dataset,data,midIndex+1,endIndex);    
	   }else {    
		   return midIndex;    
	   }    
   }   

	public static void main(String[] args) {
		int[] arr = { 6, 12, 33, 87, 90, 97, 108, 561 };
		System.out.println("循环查找元素所在位置下标：" + (loopBinarySearch(arr,90)));
		System.out.println("递归查找元素所在位置下标："+ recursionBinarySearch(arr,87,0,arr.length-1));
	}
}