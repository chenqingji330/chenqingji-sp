package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestJedisCluster {
	
	
	@Test
	public void Test1() {
		
		Set<HostAndPort> sets=new HashSet<>();
		sets.add(new HostAndPort("192.168.234.128", 7000));
		sets.add(new HostAndPort("192.168.234.128", 7001));
		sets.add(new HostAndPort("192.168.234.128", 7002));
		sets.add(new HostAndPort("192.168.234.128", 7003));
		sets.add(new HostAndPort("192.168.234.128", 7004));
		sets.add(new HostAndPort("192.168.234.128", 7005));
		JedisCluster cluster=new JedisCluster(sets);
		cluster.set("1902", "我是陈清吉");
		System.out.println(cluster.get("1902"));
	}

}
