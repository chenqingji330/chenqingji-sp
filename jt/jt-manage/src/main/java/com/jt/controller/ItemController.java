package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	// 查询商品信息 分页查询
	@RequestMapping("/query")
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		EasyUIData findItemByPage = itemService. findItemByPage(page, rows);
		return findItemByPage;

	}
	
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescByID(@PathVariable Long itemId) {
		try {
			ItemDesc findItemDescByID = itemDescService.findItemDescByID(itemId);
			
			return SysResult.ok(findItemDescByID);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return SysResult.fail();


		}
	
		
				
		
	}
	
	
	@RequestMapping("/save")
	public SysResult saveItem( Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return  SysResult.ok();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail("商品新增失败");
		}
		
	}
	
	@RequestMapping("/update")
	public  SysResult  updateItem( Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return  SysResult.ok();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail("商品修改失败");
		}
		
		
		
	}
	
	
	@RequestMapping("delete")
	public  SysResult deleteByIds(Long...ids) {
		try {
			itemService.deleteByids(ids);
			return  SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail("删除失败");
		
		}
		
		
		
	}
	//下架
	@RequestMapping("/instock")
	public SysResult  instockByids(Long...ids) {
		try {
			Integer status=2;
			itemService.instockByids(ids, status);
			return  SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail();
		}
		
		
	}
	//上架
	@RequestMapping("/reshelf")
	public SysResult  reshelfByids(Long...ids) {
		try {
			Integer status=1;
			itemService.reshelfByids(ids, status);
			return  SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail();
		}
		
		
	}
	 

}
