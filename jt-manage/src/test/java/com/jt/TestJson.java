package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

public class TestJson {
	@Test
	public void ObjJson() throws IOException {
		User u = new User();
		u.setAge(18);
		u.setName("json");
		u.setSex("男");
		u.setId(100);
		ObjectMapper mapper = new ObjectMapper();
		// 将对象转成JSON串
		String json = mapper.writeValueAsString(u);
		System.out.println(json);
		// 将json转成对象
		User u2 = mapper.readValue(json, User.class);
		System.out.println("json转对象" + u2);
	}

	@Test
	public void ListToJson() throws IOException {
		List<User> list = new ArrayList<User>();
		User u1 = new User();
		u1.setAge(18);
		u1.setName("json");
		u1.setSex("男");
		u1.setId(100);
		User u2 = new User();
		u2.setAge(17);
		u2.setName("json");
		u2.setSex("女");
		u2.setId(101);
		list.add(u1);
		list.add(u2);
		// 将List对象转成JSON串
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println(json);
		// 将json转成对象
		List list2 = mapper.readValue(json, list.getClass());
		System.out.println("json转对象" + list2);
	}

}
