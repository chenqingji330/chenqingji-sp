package com.jt;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

import redis.clients.jedis.Jedis;

public class TestRedis {
	//Mybatis  对象/集合   redis
	//序列化 反序列化 对象
	
	//String类型的操作 检查配置文件 防火墙
	//IP地址和端口号
	@Test
	public void TestString() {
		//jedis是操作redis的对象
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		String set = jedis.set("1902", "1902chenqingji My name is cheniqngji!");
		jedis.expire("1902", 300);
		String str = jedis.get("1902");
		System.out.println(str);
		
		
	}
	
	@Test
	public void TestTimeOut() {
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		jedis.setex("1903", 200,  "my name is 1903");
		System.out.println(jedis.get("1903"));
		//当key不存在时操作正常,当key存在时,操作失败  加锁用的
		Long result = jedis.setnx("1902", "bb");
		System.out.println("获取输出的数据"+result);
		
	}
	
	//3利用redis保存业务数据  数据库
	//数据库数据对象:object
	//String类型要求只能存储字符串类型
	//Item---Json 字符串
	 //实现对象转换json  
	@Test
	public void ObjectToJson() throws Exception {
		ItemDesc item = new ItemDesc();
		item.setItemId(100l).setItemDesc("我是陈清吉,你是谁,who are you!");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(item);
		System.out.println(json);
		//将json串转化为对象
		ItemDesc desc2 = mapper.readValue(json, ItemDesc.class);
		System.out.println("测试对象"+desc2);
		
	}
	@Test
	public void testSetObject() throws Exception {
		ItemDesc item = new ItemDesc();
		item.setItemId(100l).setItemDesc("我是陈清吉,你是谁,who are you!");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(item);
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		jedis.setex("item", 240, json);
		System.out.println(jedis.get("item"));
		
	}
	
	//实现List集合与Json 的转换
	@Test
	public void listToJSON() throws Exception {
		ItemDesc item1 = new ItemDesc();
		item1.setItemId(100l).setItemDesc("我是陈清吉,你是谁,who are you!");
		ItemDesc item2 = new ItemDesc();
		item2.setItemId(1l).setItemDesc(",你是谁,who are you!");
		List<ItemDesc> list = new  ArrayList<ItemDesc>();
		list.add(item1);
		list.add(item2);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		Jedis jedis = new Jedis("192.168.234.129", 6379);
		jedis.setex("list", 240, json);
		String result = jedis.get("list");
		System.out.println(result);
		
	 List<ItemDesc>list2 = mapper.readValue(result, list.getClass());
		System.out.println(list2);
		
	}
	
	
	
	//研究对象Json 的转化关系
	
	
	
	/**对象转json
	 * 1首先获取的是对象的get的方法
	 * 2将get去掉,之后首字母小写,获取属性的名称
	 * 3之后将属性的名称:属性的值进行拼接
	 * 4.形成json 串(字符串)
	 * @throws Exception
	 */
	@Test
	public void userToJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user =new User();
		user.setAge(15);
		user.setId(1);
		user.setName("陈清吉");
		user.setSex("男");
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		
	}
	/**
	 * 以下方法实现数据的转化
	 * 1获取json串
	 * 2.1通过json获取key
	 * 2根据class反射机制实例化对象
	 * 3根据key调用set方法赋值
	 * 4获得对象
	 * 5.可以利用 @JsonIgnoreProperties(ignoreUnknown=true)  //json转换过程有未知对象的时候忽略
	 * @throws Exception
	 */
	@Test
	public void jsonToUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user =new User();
		user.setAge(15);
		user.setId(1);
		user.setName("陈清吉");
		user.setSex("男");
		String json = mapper.writeValueAsString(user);
		User user1 = mapper.readValue(json, user.getClass());
		System.out.println(user1);
	}

}
