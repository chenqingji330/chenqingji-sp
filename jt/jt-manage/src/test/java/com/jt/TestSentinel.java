package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	
	@Test
	public void test01() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.234.128:26379");
		
		JedisSentinelPool sentinelPool=new JedisSentinelPool("mymaster", sentinels);
		
                    Jedis jedis = sentinelPool.getResource();
                    jedis.set("cqj", "端午节后就比较少节假日了");
                    System.out.println(jedis.get("cqj"));
                    jedis.close();
                    
		
	}

}
