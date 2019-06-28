package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedis;

	@Override
	public void saveUser(User user) {
		//1 将密码加密
		String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5);
		//2补齐入库数据
		user.setCreated(new Date()).setUpdated(user.getCreated());
		user.setEmail(user.getPhone()+"@163.com");
		userMapper.insert(user);
	
	
		
	}
/**
 * 1校验用户名或者密码是否正确
	2如果数据不正确,返回null
	3如果数据正确, 1)(生成秘钥MD5(JT_TICKET_+username+当前毫秒数);2)将userDB数据转换为userJSON 3)将数据保存到redis中7天超时
	4返回token
 */
	@Override
	public String findUserByUP(User user) {
		String token =null;
		//1校验用户名或者密码是否正确
		//1.1将密码进行加密
		String  md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		//2如果数据不正确,返回null
		if(userDB!=null) {
			//判断数据正确,执行下列代码
			token="JT_TICKET_"+userDB.getUsername()+System.currentTimeMillis();
			token=DigestUtils.md5DigestAsHex(token.getBytes());
			//脱敏处理,一切从业务出发
			userDB.setPassword("你猜猜!!!");
			String userJson = ObjectMapperUtil.toJson(userDB);
			//将数据缓存到redis服务器
			jedis.setex(token, 7*24*3600, userJson);
			
		}
		//3如果数据正确, 1)(生成秘钥MD5(JT_TICKET_+username+当前毫秒数);2)将userDB数据转换为userJSON 3)将数据保存到redis中7天超时
		//4返回token
		
		return token;
	}
@Override
public void deleteToken(String token) {
	
	jedis.del(token);

	
	
}
	
	 

}
