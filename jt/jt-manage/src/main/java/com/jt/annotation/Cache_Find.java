package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jt.enu.KEY_ENUM;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache_Find {
	String key()  default "";
	KEY_ENUM keyType() default KEY_ENUM.EMPTY;
	int seconds()		default 0;	
	
	

}
