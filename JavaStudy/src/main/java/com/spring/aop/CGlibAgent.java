/**
 * 
 */
package com.spring.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @ClassName CGlibAgent
 * @Description 
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:59:57
 */
public class CGlibAgent implements MethodInterceptor {
	
	private Object proxy;
	
	public Object getInstance(Object proxy) {
		this.proxy = proxy;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.proxy.getClass());
		// 回调方法
		enhancer.setCallback(this);
		// 创建代理对象
		return enhancer.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println(">>> before invoking");
		Object ret = methodProxy.invokeSuper(o, objects);
		System.out.println(">>> after invoking");
		return ret;
	}
	// CGlib可以直接对实现类进行操作而非接口
	public static void main(String[] args) {
		CGlibAgent cGlibAgent = new CGlibAgent();
		Apple apple = (Apple) cGlibAgent.getInstance(new Apple());
		apple.show();
	}
}
