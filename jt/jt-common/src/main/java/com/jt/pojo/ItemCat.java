package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@ToString
@NoArgsConstructor
@TableName("tb_item_cat")
public class ItemCat extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4880709864387816419L;
	@TableId(type=IdType.AUTO)
	private Long id;
	private Long parentId;
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent;
	 
	
	
	


//id                               bigint(20)类目ID
//parent_id                  bigint(20)父类目ID=0时，代表的是一级的类目
//namevar                 char(50)类目名称
//status                   int(1)状态。可选值:1(正常),2(删除)
//sort_order             int(4)排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
//is_parent                 tinyint(1)该类目是否为父类目，1为true，0为false
//createddate              time创建时间
//updateddate             time创建时间

}
