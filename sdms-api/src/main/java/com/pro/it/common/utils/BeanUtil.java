package com.pro.it.common.utils;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class BeanUtil {
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Object getValue(Object bean, String fieldName) throws Exception {
		Field field = bean.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(bean);
	}
	
	public static void setValue(Object bean, String fieldName, Object value) throws Exception {
		Field field = bean.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(bean, value);
	}
	
	public static String toJson(Object bean) throws Exception {
		return objectMapper.writeValueAsString(bean);
	}

}
