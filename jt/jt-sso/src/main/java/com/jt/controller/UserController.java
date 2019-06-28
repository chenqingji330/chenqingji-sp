package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedis;
	
	/**
	 * 校验用户是否存在
	 * 由于是跨域的请求,返回值必须特殊的处理
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject  checkUserName(@PathVariable String param,@PathVariable Integer type,String callback) {
		
		JSONPObject object=null;
		try {
		boolean flag=	userService.checkUserName(param,type);
		object=new JSONPObject(callback, SysResult.ok(flag));
			
		} catch (Exception e) {
		e.printStackTrace();
		object=new JSONPObject(callback, SysResult.fail());
		}
		
		return object;

				 
		
	}
//	请求方法	GET
//	URL	http://sso.jt.com/user/check/{param}/{type}
//	参数	格式如：chenchen/1
//	其中chenchen是校验的数据
//	Type为类型，可选参数1 username、2 phone、3 email
//	示例	http://sso.jt.com/user/check/chenchen/1
	
	@RequestMapping("/register")
	public SysResult saveUser(String userJson) {
		
		try {
			
		User user = ObjectMapperUtil.jsonToObject(User.class, userJson);
			
			userService.saveUser(user);
		return 	SysResult.ok();
		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		
		}
		
	}
	
	
	/**
	 * 	利用跨域实现用户信息回显
	 * http://sso.jt.com/user/query/73d9809e7972bbf330f1fb35cb638d28?callback=jsonp1560762546338&_=1560762546390
	 * @return
	 */
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		
		String userJSON = jedis.get(ticket);
		if(StringUtils.isEmpty(userJSON)) 
			
			return new JSONPObject(callback, SysResult.fail());
			
		else
		
		return new  JSONPObject(callback, SysResult.ok(userJSON)) ;
		
	}
	
}
