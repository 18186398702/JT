package com.jt.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该工具类实现了ObjectMapper对象的转化
 * 优化了异常的try-catch
 * @author C&J
 *
 */
public class MapperUtil {

	//将对象转化成json的写法
	public static String toJSON(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	//将json数据转化成对象
	public static <T>T toObject(String json,Class<T> targetClass){
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return  object;
	}
	
}
