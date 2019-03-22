package sourceCodeTest;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {
	
	public static void main(String[] args) {
		// LinkedList 实现了 Deque，Deque继承自Queue
		Queue<String> queue = new LinkedList<String>();
		queue.offer("string");
		queue.offer("the");
		queue.offer("cat");
		System.out.println(queue.poll());
		System.out.println(queue.remove());
		System.out.println(queue.size());
	}
	
}
