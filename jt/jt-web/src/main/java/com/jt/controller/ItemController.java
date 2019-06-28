package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService ItemService;
	
	/**
	 * 根据商品的id查服务器数据
	 * 业务步骤:
	 * 1.在前台的service中实现httpclient的调用的
	 * 2.后台根据itemId查询数据对象的json串
	 * 3.前台将json转化为item对象
	 * 4将item保存到request域
	 * 5返回页面逻辑名称item
	 */
	@RequestMapping("/items/{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model) {
		Item item=ItemService.findItemById(itemId);
		
		ItemDesc itemDesc= ItemService.findItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
		
	}

}
