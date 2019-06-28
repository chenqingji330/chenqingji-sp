package com.jt.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.pojo.User;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired(required=false)
	private HttpClientService client;

	@Override
	public void saveUser(User user) {
		
		String url="http://sso.jt.com/user/register";
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5DigestAsHex);
		user.setEmail(user.getPhone());
		String json = ObjectMapperUtil.toJson(user);
		Map<String,String> params=new  HashMap<>();
		
		params.put("userJson", json);
		//执行远程访问请求
		String doPost = client.doPost(url, params);
		System.out.println(doPost);
		
		
	}

}
