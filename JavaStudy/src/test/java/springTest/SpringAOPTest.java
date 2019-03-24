package springTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.aop.Subject;

public class SpringAOPTest {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("SpringAOP.xml");
		Subject s1 = (Subject) ctx.getBean("SubjectImpl1");
		Subject s2 = (Subject) ctx.getBean("SubjectImpl2");
		
		s1.login();
		s1.download();
		
		System.err.println("================");
		
		s1.login();
		s1.download();
	}
}
