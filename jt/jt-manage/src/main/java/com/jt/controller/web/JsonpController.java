package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JsonpController {
	
	
	//@RequestMapping("/web/testJSONP")
	public String testJsonp(String callback) {	
		User user=new User();
		
		user.setUsername("陈清吉");
		String json = ObjectMapperUtil.toJson(user);
		System.out.println(callback+"("+json+")");
		return callback+"("+json+")";
		
	}
	
	@RequestMapping("/web/testJSONP")
public JSONPObject jsonp(String callback) {
         User user=new User();
         user.setUsername("陈清吉");
		JSONPObject object=new JSONPObject(callback, user);
		
		return object;
	
}
}
