package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

public interface ItemService {

	EasyUIData findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item,ItemDesc itemDesc);

	void deleteByids(Long[] ids);

	void instockByids(Long[] ids, Integer status);

	void reshelfByids(Long[] ids, Integer status);

	Item findItemById(Long id);

	ItemDesc findItemDescById(Long id);

	
	
}
