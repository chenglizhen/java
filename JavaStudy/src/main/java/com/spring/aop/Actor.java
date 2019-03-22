/**
 * 
 */
package com.spring.aop;

/**
 * @ClassName Actor
 * @Description 真实实体类
 * @Author Cheng Lizhen
 * @Date 2019年3月21日 上午11:01:11
 */
public class Actor implements Person{
	
	private String content; // 说话的内容，Actor类的私有属性
	
	// 构造函数
	public Actor(String content) {
		this.content = content;
	}
	
	@Override
	public void speak() {
		System.out.println(this.content);
	}

}
