package com.jt;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {

	// 测试静态工厂 从容器中获取calendar
	@Test
	public void testStatic() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring/factory.xml");
		Calendar calendar = (Calendar) context.getBean("calendar1");
		System.out.println(calendar.getTime());
	}

	// 测试实例工厂 从容器中获取calendar
	@Test
	public void testInstance() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring/factory.xml");
		Calendar calendar = (Calendar) context.getBean("calendar2");
		System.out.println(calendar.getTime());
	}

	// 测试实例工厂 从容器中获取calendar
	@Test
	public void testSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring/factory.xml");
		Calendar calendar = (Calendar) context.getBean("calendar3");
		System.out.println(calendar.getTime());
	}

}
