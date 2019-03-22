/**
 * 
 */
package com.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName DynamicAgent
 * @Description 动态代理类
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:28:30
 */
public class DynamicAgent{
	
	// 实现InvocationHandler接口，并初始化被代理类的对象
	static class MyHandler implements InvocationHandler {
		
		private Object proxy; // 被代理的类的实例
		
		public MyHandler(Object proxy) {
			this.proxy = proxy;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println(">>>before invoking");
			// 真正调用方法的地方
			Object ret = method.invoke(this.proxy, args);
			System.out.println(">>>after invoking");
			return ret;
		}
	}
		
		/**
		 * @param interfaceClazz 被代理的类（接口类）
		 * @param proxy 被代理的类的实例
		 * @return 返回一个被修改过的对象
		 */
		public static Object agent(Class interfaceClazz, Object proxy) {
			return Proxy.newProxyInstance(interfaceClazz.getClassLoader(), new Class[]{interfaceClazz}, 
					new MyHandler(proxy));
		}
		
}
