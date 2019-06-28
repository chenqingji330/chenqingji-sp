package com.jt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	

	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		int start=(page-1)*rows;
		int pageSize=rows;
		int total=itemMapper.selectCount(null);
		
		List<Item> list = itemMapper.findItemByPage(start,pageSize);
	
		return new EasyUIData(total,list);
	}

	@Override
	@Transactional//添加事务控制
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
		
		
		
		
	}
	@Transactional//添加事务控制
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//同时修改2张表数据
				itemDesc.setItemId(item.getId())
						.setUpdated(item.getUpdated());
				itemDescMapper.updateById(itemDesc);
		
		
	}

	@Transactional//添加事务控制
	@Override
	public void deleteByids(Long[] ids) {
		//方式1  循环遍历按照id删除,调用mybatis-plus
//		for (Long id : ids) {
//		 itemMapper.deleteById(id);
//			
//		}
//		
		//方式2  使用传统的mapper方式删除
//		itemMapper.deleteByids(ids);
	
		//方式3
//		ArrayList idList = new ArrayList();
//		for (Long id : ids) {
//			idList.add(id);
//			
//		}
//		itemMapper.deleteBatchIds(idList);
//		
		
		//方式4
		List<Long> asList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(asList);
		
	}

	@Override
	public void instockByids(Long[] ids, Integer status) {
		
		Item entity = new Item();
		entity.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new  UpdateWrapper<Item>();
		
		updateWrapper.in("id", Arrays.asList(ids));
		
		itemMapper.update(entity, updateWrapper);
		
	
		
	}

	@Override
	public void reshelfByids(Long[] ids, Integer status) {
		Item entity = new Item();
		entity.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper = new  UpdateWrapper<Item>();
		
		updateWrapper.in("id", Arrays.asList(ids));
		
		itemMapper.update(entity, updateWrapper);
		
		
	}

	@Override
	public Item findItemById(Long id) {
		return  itemMapper.selectById(id);
		
	}
	
	@Override
	public ItemDesc findItemDescById(Long id) {
		return 	itemDescMapper.selectById(id);
		
	}
	

	
	}
	
	
	
	
	
	
	

