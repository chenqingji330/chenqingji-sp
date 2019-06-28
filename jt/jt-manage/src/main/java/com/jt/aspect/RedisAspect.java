package com.jt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.RedisService;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

@Component//交给spring容器管理
@Aspect  //标识为切面
public class RedisAspect {
//	@Autowired(required=true)
//	private ShardedJedis jedis;

	@Autowired(required=false)
	private JedisCluster jedis;
	
	//注入哨兵的工具api
//	@Autowired(required=false)
//	private RedisService jedis;

	//引入哨兵  1000个链接最佳
//	@Autowired
//	private JedisSentinelPool pool;

	
	
	@Around("@annotation(cache_find)")
	public Object around
	(ProceedingJoinPoint joinPoint,Cache_Find cache_find) {
		//1.获取key
		String key = getKey(joinPoint,cache_find);
	
		//2.获取缓存数据
		
		String result = jedis.get(key);
		Object data = null;
		//3.判断结果是否有数据
		try {if(StringUtils.isEmpty(result)) {
			//表示缓存中没有数据,查询数据库
			data = joinPoint.proceed();
			String json = ObjectMapperUtil.toJson(data);
			if(cache_find.seconds() == 0)
				jedis.set(key,json);	//表示永不超时
			else
				jedis.setex(key,cache_find.seconds(), json);
			System.out.println("查询数据库");
			
		}else {
			Class targetClass = getTargetClass(joinPoint);
			data = ObjectMapperUtil.jsonToObject(targetClass,result);
			System.out.println("AOP查询缓存!!!");
		}
			
			
		} catch ( Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
		return data;
		
	}

	private Class getTargetClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature =(MethodSignature) joinPoint.getSignature();
		Class returnType = signature.getReturnType();
		return returnType;
	}

	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
		//1.获取key类型
		KEY_ENUM key_ENUM = cache_find.keyType();
		
		//2.判断key类型
		if(key_ENUM.equals(KEY_ENUM.EMPTY)) {
			//表示使用用户自己的key
			return cache_find.key();
			
		}
		//表示用户的key需要拼接  key+"_"+第一个参数
		String strArgs = String.valueOf(joinPoint.getArgs()[0]);
		String key =  cache_find.key()+strArgs;
		return key;
	}
	
	

}
