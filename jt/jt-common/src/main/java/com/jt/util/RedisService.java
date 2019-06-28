package com.jt.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	@Autowired(required=false)//调用时注入
	private JedisSentinelPool  sentinelPool;
	
	
	//封装方法  get方法
	public  String get(String key) {
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
		
	}
	
	
	//set方法
	public String set(String key, String value) {
		Jedis jedis = sentinelPool.getResource();
		String set = jedis.set(key, value);
		jedis.close();
		return set;	
	}
	
	//setex方法
	 public  String setex(String key, int seconds, String value) {
		 Jedis jedis = sentinelPool.getResource();
		 String setex = jedis.setex(key, seconds, value);
		 jedis.close();
		 return setex;
	 }
	
}
