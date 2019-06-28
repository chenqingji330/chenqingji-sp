package com.jt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Accessors(chain=true)
    @JsonIgnoreProperties(ignoreUnknown=true)  //json转换过程有未知对象的时候忽略
	 public class User  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6772631150556600593L;
		private Integer id;
		private String name;
		private String sex;
		private Integer age;

	
	 }

