/**
 * 
 */
package springTest;

import java.lang.reflect.InvocationTargetException;

import com.spring.aop.Apple;
import com.spring.aop.DynamicAgent;
import com.spring.aop.Fruit;

/**
 * @ClassName ReflectTest
 * @Description 动态代理反射测试类
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:45:53
 */
public class ReflectTest {

	public static void main(String[] args) throws InvocationTargetException, IllegalAccessException{
		//注意一定要返回接口，不能返回实现类否则会报错
		Fruit fruit = (Fruit) DynamicAgent.agent(Fruit.class, new Apple());
		fruit.show();
	}
	//可以看到对于不同的实现类来说，可以用同一个动态代理类来进行代理，实现了“一次编写到处代理”的效果。
	//但是这种方法有个缺点，就是被代理的类一定要是实现了某个接口（Apple类实现了Fruit类），这很大程度限制了本方法的使用场景。
}
