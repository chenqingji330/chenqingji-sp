package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/getMsg")
	public String getMsg() {
		return "我的端口号是8093";
				
	}

}
