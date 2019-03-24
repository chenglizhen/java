package com.spring.aop;

/**
 * @author cheng
 * 定义主题接口，这些接口的方法可以成为我们的"连接点"
 */
public interface Subject {

	// 登陆
	void login();
	
	// 下载
	void download();
}
