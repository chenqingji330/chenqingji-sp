package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User extends BasePojo {

		/**
	 * 
	 */
	private static final long serialVersionUID = 7485683772893975616L;
		@TableId(type=IdType.AUTO)
	   private Long id;//                 bigint not null auto_increment,
	   private String username ; //           varchar(50),
	   private String password  ;//           varchar(32) comment 'MD5加密',
	   private String phone          ;//      varchar(20),
	   private String email     ;//           varchar(50),
	 //  created              datetime,
	//   updated              datetime,
	 //  primary key (id)

}
