package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import com.jt.vo.EasyUIData;

public interface ItemMapper extends BaseMapper<Item>{

	@Select("select * from tb_item order by updated desc limit #{start},#{pageSize}")
	List<Item> findItemByPage(
			@Param("start") Integer start, 
			@Param("pageSize")Integer pageSize);

	void deleteByids(Long[] ids);
	
}
