package sourceCodeTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author cheng
 * 使用Collections.unmodifiableCollection(Collection c)方法来创建一个只读集合，
 * 这样改变集合的任何操作都会抛出Java.lang.UnsupportedOperationException异常。
 */
public class UnmodifiableListTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("x");
		Collection<String> clist = Collections.unmodifiableCollection(list);
		clist.add("y"); 
		System.out.println(list.size());
	}

	
}
