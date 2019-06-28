package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.service.ItemCartService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCartController {
	@Autowired
	private ItemCartService itemCartService;
	
	@RequestMapping("/queryItemName")
	public String queryItemNameByID(Long itemCatId) {
		String queryItemNameByID = itemCartService.queryItemNameByID(itemCatId);
		return queryItemNameByID;
	}
	//需要获取任意的名称的参数,为指定的参数名称赋值, value="id",defaultValue="0" 如果有值就按照传递的值,如果没有就按照默认值0
	@RequestMapping("/list")
	@Cache_Find(key="ITEM_CAT_",keyType=KEY_ENUM.AUTO )
	public List<EasyUITree> findItemCatByCache(@RequestParam(value="id",defaultValue="0") Long parent_id) {
		
		
		List<EasyUITree> findItemCatByParentID = itemCartService.findItemCatByParentID(parent_id);
		
		return findItemCatByParentID;
		//return itemCartService.findItemCatByCache( parent_id);
		
		
	}
	
	

}
