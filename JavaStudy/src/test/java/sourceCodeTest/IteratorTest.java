package sourceCodeTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("hello");
		list.add("every");
		list.add("one");
		Iterator<String> it = list.iterator();
		// remove()方法在next()前调用，或者在remove()后再次调用，均抛出IllegalStateException异常。
		while(it.hasNext()) {
//			it.remove();
			String obj = it.next();
			list.remove(obj); // 抛出 ConcurrentModificationException 异常
			System.out.println(obj);
		} 
//		it.remove();
//		it.remove();
		
	}
	
}
