package javaBasics;

import java.util.HashMap;
import java.util.Map;

public class Exceptions {
	
	public static void main(String[] args) {
		Map m = new HashMap<>();
		m.put("a", "2");
		Integer it = (Integer) m.get("a");
		System.out.println(it);
	}
}
