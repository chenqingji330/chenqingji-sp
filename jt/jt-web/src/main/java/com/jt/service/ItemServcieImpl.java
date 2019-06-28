package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServcieImpl implements ItemService {
	@Autowired
	private HttpClientService httpClient;

	@Override
	public Item findItemById(Long itemId) {
	String url=  "http://manage.jt.com/web/item/findItemById";
	Map<String,String> params=new HashMap<>();
	params.put("id", itemId+"");
	String result = httpClient.doGet(url, params);
	Item item = ObjectMapperUtil.jsonToObject(Item.class, result);
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		//http://manage.jt.com/web/item/findItemDescById?id=1474391935
		String url="http://manage.jt.com/web/item/findItemDescById";
		Map<String,String> params=new HashMap<>();
		params.put("id", itemId+"");
		String result = httpClient.doGet(url, params);
		ItemDesc itemdesc = ObjectMapperUtil.jsonToObject(ItemDesc.class, result);
			return itemdesc;
		
	}

}
