package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
     //true表示用户已经存在,false表示不存在
	/**
	 * 1param用户参数
	 * 2type 类型  1 username、2 phone、3 email
	 * 
	 * 将type转换为具体的字段
	 */
	@Override
	public boolean checkUserName(String param, Integer type) {
		String colum= type==1?"username":(type==2?"phone":"email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq(colum, param);
		int count = userMapper.selectCount(queryWrapper);
		
	
		return count==0?false:true;
	}
	
	@Override
	public void saveUser(User user) {
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	
		
	}
	
}
