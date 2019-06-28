package com.jt;

import org.junit.Test;

import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		jedis.lpush("list","1","2","3","4");
		System.out.println(jedis.rpop("list"));
		
		
	}
	@Test
	public void testTx() {
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		Transaction multi = jedis.multi();
		try {
			multi.set("aaaaa", "aaa");
			multi.set("bbbb", "bbbb");
			multi.exec();
			
		} catch (Exception e) {
			multi.discard();
		
		}
	}
	@Test
	public void testUtil() {
		ObjectMapperUtil util = new ObjectMapperUtil();
		User user = new User();
		user.setAge(15).setId(12).setName("cqj ").setSex("男");
	//	Jedis jedis = new Jedis("192.168.234.129", 6379);
		String json = util.toJson(user);
		System.out.println(json);
	//	String set = jedis.set("item", json);
	//	System.out.println(set);
	}
	

}
