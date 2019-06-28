package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCartMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;


@Service
public class ItemCartServiceImpl implements ItemCartService {
	 @Autowired
	private ItemCartMapper itemCartMapper;
//	 @Autowired
//     private  Jedis jedis;
  
	@Override
	public String queryItemNameByID(Long itemCatId) {
		String name = itemCartMapper.selectById(itemCatId).getName();
		return name;
	}

//	@Override
//	public List<EasyUITree> findItemCatByCache(Long parent_id) {
//		String key="ITEM_CAT_"+parent_id;
//		String result=jedis.get(key);
//		List<EasyUITree> List = new ArrayList<EasyUITree>();
//		if(StringUtils.isEmpty(result)) {
//		List<EasyUITree> listTree = findItemCatByParentID(parent_id);
//		String json = ObjectMapperUtil.toJson(listTree);
//		@SuppressWarnings("unused")
//		String setex = jedis.setex(key, 60*60*24*7, json);
//		System.out.println("from data");
//			return listTree;
//		} else {
//			@SuppressWarnings("unchecked")
//			List<EasyUITree> jsonToObject = ObjectMapperUtil.jsonToObject(List.getClass(), result);
//			System.out.println("from redis");
//			return jsonToObject;
//			
//			
//		}
//	
//	}

	@Override
	 public List<EasyUITree> findItemCatByParentID(Long parent_id) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parent_id);
		List<ItemCat> selectList = itemCartMapper.selectList(queryWrapper);
		List<EasyUITree> list = new ArrayList<EasyUITree>();
		
		for (ItemCat itemCat : selectList) {
		
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId());
			easyUITree.setText(itemCat.getName());
			
			String state=itemCat.getIsParent()?"closed":"open";
			easyUITree.setState(state);
			list.add(easyUITree);
		
		}
		
		return list;
	}
	}
	
	
	
	
	
	
	
	
	
	
	

