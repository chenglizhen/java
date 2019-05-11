package com.thread;

/**
 * @author cheng
 * 通过继承Thread类来创建并启动多线程的一般步骤如下：
 * 1、定义Thread类的子类，并重写该类的run()方法，该方法的方法体就是线程需要完成的任务，run()方法也称为线程执行体。
 * 2、创建Thread子类的实例，也就是创建了线程对象
 * 3、启动线程，即调用线程的start()方法
 *
 */
public class FirstThreadTest extends Thread {
	// 重写run() 方法
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(getName() + "  " + i);//getName():返回调用该方法的线程的名字。
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			//Thread.currentThread():返回当前正在执行的线程对象
			System.out.println(Thread.currentThread().getName() + "  : " + i);
			if (i == 20) {
				new FirstThreadTest().start(); // 创建并启动线程
				new FirstThreadTest().start();
			}
		}
	}
}
