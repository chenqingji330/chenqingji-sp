package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String redisNodes;

	
	@Bean
	public JedisCluster jedisCluster() {
		
		Set<HostAndPort> sets=new HashSet<>();
		String[] nodesInfo = redisNodes.split(",");
		for(String node:nodesInfo) {
			String[] nodeInfo = node.split(":");
			String host=nodeInfo[0];
			int port=Integer.parseInt(nodeInfo[1]);
			HostAndPort hp=new HostAndPort(host, port);
			sets.add(hp);			
		}
		return new  JedisCluster(sets);		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Value("${redis.masterName}")
//	private String masterName;
//	@Value("${redis.sentinels}")
//	private String sentinelsNodes;
//	
//	
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels=new HashSet<>(); 
//		sentinels.add(sentinelsNodes);
//		return new JedisSentinelPool(masterName, sentinels);
//		
//	}
//	
//	@Value("${jedis.nodes}")
//	private String nodes;
//	@Bean
//	public ShardedJedis getShards() {
//		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();	
//		String[] nodesInfo = nodes.split(",");
//		for (String str : nodesInfo) {
//			String[] node = str.split(":");
//			String host = node[0];
//			int port = Integer.parseInt(node[1]);
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(host,port);
//			shards.add(jedisShardInfo);	
//			
//		}
//		return  new ShardedJedis(shards);
//	}

}
