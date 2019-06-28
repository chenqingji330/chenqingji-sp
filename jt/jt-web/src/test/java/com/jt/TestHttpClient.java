package com.jt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient {
	@Autowired
	private CloseableHttpClient client;
	@Autowired
	private HttpClientService httpClientService;
	
	/**
	 * 测试HttpClient
	 * 1实例化HttpClient对象
	 * 2定义http请求路径
	 * 3定义请求方式get/post
	 * 4利用api发起http请求
	 * 5获取返回值后判断状态信息200
	 * 6获取响应信息
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 * 
	 * 
	 */
	@Test
	public void TestGet() throws ClientProtocolException, Exception {
	//	CloseableHttpClient client= HttpClients.createDefault();
		String url="http://www.tmooc.cn/";
		
		HttpGet hpget=new HttpGet(url);

		CloseableHttpResponse response=client.execute(hpget);
		if(response.getStatusLine().getStatusCode()==200) {
			System.out.println("恭喜您访问成功");
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File("D:/百度.html")),"UTF-8");
			out.write(result);
			out.close();
			
			System.out.println(result);
			
			
		}else {
			throw new RuntimeException();
		}
		
		
	}
	
	@Test
	public void testUtil() {
		String url="http://manage.jt.com";
		String doGet = httpClientService.doGet(url);
		System.out.println(doGet);
		
	}

}
