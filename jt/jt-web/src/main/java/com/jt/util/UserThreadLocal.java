package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
	
	private static ThreadLocal<User>  thread=new ThreadLocal<>();
	//将对象存入线程中
	public static void set(User user) {
		thread.set(user);
		
	}
	//从线程中获取对象
	public static User get() {
		return thread.get();
	}
	//切记内存泄漏,及时关闭线程
	public static void remove() {
		thread.remove();
		
	}

}
