package com.jt.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HttpClientService {
	@Autowired
	private CloseableHttpClient client;
	@Autowired
	private  RequestConfig requestConfig;
	
	
	public String doGet(String url,Map<String,String> params,String charset) {
		
		//1 校验字符集的编码格式
		if(StringUtils.isEmpty(charset)) {
			//表示如果没有指定字符集,则设置为UTF-8
			charset="UTF-8";
		}
	//2校验Map集合是否为空
		if(params!=null) {
//			Set<String> keySet = params.keySet();
//			for (String key : keySet) {
//				String str=key+"="+params.get(key);
//				
//			}
			url+="?";
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) { 
				
				String key = entry.getKey();
				String value = entry.getValue();
				url=url+key+"="+value+"&";
				
			}
			url=url.substring(0, url.length()-1); 
		}
		
		//定义请求对象
		HttpGet hpget=new HttpGet(url);
		hpget.setConfig(requestConfig);
		String result=null;
		try {
			CloseableHttpResponse response = client.execute(hpget);
			HttpEntity entity = response.getEntity();
			result=EntityUtils.toString(entity,"utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
	
	}
		
		return result;
		}
	
	
	
	public String doGet(String url) {
		return doGet(url,null,null);
		}
	
	public String doGet(String url,Map<String,String> params) {
		return doGet(url,params,null);
		}
	
	//实现httpClient POST提交
		public String doPost(String url,Map<String,String> params,String charset){
			String result = null;

			//1.定义请求类型
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);  	//定义超时时间

			//2.判断字符集是否为null
			if(StringUtils.isEmpty(charset)){

				charset = "UTF-8";
			}

			//3.判断用户是否传递参数
			if(params !=null){
				//3.2准备List集合信息
				List<NameValuePair> parameters = 
						new ArrayList<>();

				//3.3将数据封装到List集合中
				for (Map.Entry<String,String> entry : params.entrySet()) {

					parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}

				//3.1模拟表单提交
				try {
					UrlEncodedFormEntity formEntity = 
							new UrlEncodedFormEntity(parameters,charset); //采用u8编码

					//3.4将实体对象封装到请求对象中
					post.setEntity(formEntity);
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
			}

			//4.发送请求
			try {
				CloseableHttpResponse response = 
						client.execute(post);

				//4.1判断返回值状态
				if(response.getStatusLine().getStatusCode() == 200) {

					//4.2表示请求成功
					result = EntityUtils.toString(response.getEntity(),charset);
				}else{
					System.out.println("获取状态码信息:"+response.getStatusLine().getStatusCode());
					throw new RuntimeException();
				}
			} catch (IOException e) {
			
				e.printStackTrace();
			}

			return result;
		}



		public String doPost(String url){

			return doPost(url, null, null);
		}

		public String doPost(String url,Map<String,String> params){

			return doPost(url, params, null);
		}

		public String doPost(String url,String charset){

			return doPost(url, null, charset);
		}

}
