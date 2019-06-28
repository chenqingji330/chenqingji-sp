package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EasyUIData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7593775376565877096L;
	private Integer total;  //记录条数
	private List<Item> rows; //记录显示条目

}
