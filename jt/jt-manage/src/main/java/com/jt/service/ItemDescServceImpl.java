package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
@Service
public class ItemDescServceImpl implements ItemDescService {
	@Autowired
	 private ItemDescMapper itemDescMapper;

	@Override
	public ItemDesc findItemDescByID(Long itemId) {
		
		ItemDesc selectById = itemDescMapper.selectById(itemId);
		
		return selectById;
	}

}
