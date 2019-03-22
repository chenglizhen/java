/**
 * 
 */
package com.spring.aop;

/**
 * @ClassName Agent
 * @Description 静态代理类
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:03:35
 */
public class StaticAgent implements Person{

	private Actor actor;
	private String before;
	private String after;
	
	public StaticAgent(Actor actor, String before, String after) {
		this.actor = actor;
		this.before = before;
		this.after = after;
	}
	
 	@Override
	public void speak() {
 		System.out.println("Agent Speak:" + before);
 		this.actor.speak();
 		System.out.println("Agent Speak:" + after);
 		
	}
	
}
