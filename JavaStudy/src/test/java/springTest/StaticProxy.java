/**
 * 
 */
package springTest;

import com.spring.aop.Actor;
import com.spring.aop.StaticAgent;

/**
 * @ClassName StaticProxy
 * @Description 静态代理测试类
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:13:55
 */
public class StaticProxy {

	public static void main(String[] args) {
		Actor actor = new Actor("I'm a actor");
		StaticAgent agent = new StaticAgent(actor, "I'm a agent", "that's all");
		agent.speak();
	}
}
