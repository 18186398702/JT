package com.jt.manage.factory;

import java.util.Calendar;
import java.util.List;

public class StaticFactory {
	
	//必须有静态方法
	public static Calendar getInstance() {
		return Calendar.getInstance();
	}
}
