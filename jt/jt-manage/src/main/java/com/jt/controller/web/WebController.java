package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jt.service.ItemService;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

@RestController
@RequestMapping("/web/item")
public class WebController {
	@Autowired
	private ItemService   ItemService;

	
	@RequestMapping("/findItemById")
	public Item findItemById(Long id) {
		
		return ItemService.findItemById(id);
		
	}
	
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long id) {
		
		return ItemService.findItemDescById(id);
		
	}

}
