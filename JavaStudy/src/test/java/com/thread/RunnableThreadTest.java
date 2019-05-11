package com.thread;

/**
 * @author cheng
 * 通过实现Runnable接口创建并启动线程一般步骤如下：
 * 1、定义Runnable接口的实现类，并重写run()方法，run()方法也就是线程的执行体
 * 2、创建Runnable实现类的实例，并用这个实例作为Thread的target来创建Thread对象，这个Thread对象才是真正的线程对象
 * 3、调用线程对象的start()方法来启动线程
 */
public class RunnableThreadTest implements Runnable {

	private int i;

	// 重写run()方法
	public void run() {
		for (i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
			if (i == 20) {
				RunnableThreadTest rtt = new RunnableThreadTest();
				new Thread(rtt, "新线程1").start();
				new Thread(rtt, "新线程2").start();
			}
		}
	}
}