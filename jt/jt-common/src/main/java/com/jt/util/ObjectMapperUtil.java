package com.jt.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 编辑工具类实现对象与json的转化
 * @author UID
 *
 */
public  class ObjectMapperUtil {
	
	private static final ObjectMapper Mapper=new ObjectMapper();
	
	public static String toJson(Object target) {
		String json=null;
		try {
			 json = Mapper.writeValueAsString(target);
				return json;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		
		}
	
		
	}
	
	public static <T> T jsonToObject(Class<T> cls,String json){
		T target=null;
		try {
			 target = Mapper.readValue(json, cls);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);		
		}	
	return target;
		
	}

}
