package com.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Aspect // 声明自己是一个切面类
public class MyAspect {
	
	// @Before是增强中的方位，括号中的就是切入点
	@Before("execution(* com.spring.aop..*(..))")
	public void before() {
		System.out.println("前置通知");
	}

	public static void main(String[] args) {
        ApplicationContext ac =new ClassPathXmlApplicationContext("applicationContext.xml");
        Dog dog =(Dog) ac.getBean("dog");
        System.out.println(dog.getClass());
        dog.say();

	}
}
