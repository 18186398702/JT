package com.jt.web.util;

import com.jt.common.po.User;

public class UserThreadLocal {
	
	private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
	
	public static void set(User user) {
		userThreadLocal.set(user);
	}
	
	public static User get() {
		return userThreadLocal.get();
	}
	
	//用完ThreadLocal切记删除对象
	public static void remove() {
		userThreadLocal.remove();
	}
	
}
