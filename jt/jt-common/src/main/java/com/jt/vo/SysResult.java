package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@ToString
public class SysResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7004985513093683827L;
	private Integer status;//200表示成功,201失败
	private String msg; //后台返回的提示信息
	private Object data;//后台返回的任意数据
	
	public static SysResult ok( ) {
		return new SysResult(200,null,null);
	
	}
	
	public static SysResult ok(Object data ) {
		return new SysResult(200,null,data);
	
	}
	
	public static SysResult ok(String msg,Object data ) {
		return new SysResult(200,msg,data);
	
	}
	
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}
	
	public static SysResult fail(String msg) {
		return new SysResult(201,msg,null);
	}
	
	

}
