package com.spring.aop;

/**
 * @author cheng
 * 定义实现类,这是代理模式中真正的被代理人，即动作的执行者
 */
public class SubjectImpl implements Subject{

	@Override
	public void login() {
		System.out.println("借书中...");
		
	}

	@Override
	public void download() {
		System.out.println("下载中...");
	}

}
