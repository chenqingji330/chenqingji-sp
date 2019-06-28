package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITree;

public interface ItemCartService {

	String  queryItemNameByID(Long itemCatId);

List<EasyUITree> findItemCatByParentID(Long parent_id);

	//List<EasyUITree> findItemCatByCache(Long parent_id);



	
	
}
