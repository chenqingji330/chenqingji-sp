package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/user")
public class UserController {
//	@Autowired
//	private UserService userService;
	
	//导入dubbo接口
	@Reference(timeout=3000,check=false)
	private DubboUserService userService;
	
	@RequestMapping("/{modelName}")
	public String  index(@PathVariable String modelName) {
		return  modelName;
		
	}
	
	//   /user/doRegister
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		
		try {
			userService.saveUser(user);
			return SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		
		}
		return SysResult.fail();	
	}
	
	//实现用户登陆
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {	
		try {		
			//调用sso系统获取秘钥
			String token=userService.findUserByUP(user);
			System.out.println("我要准备登陆了");
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie=new Cookie("JT_TICKET",token);
				/**
				 * cookie.setMaxAge(值)    值=0,立即删除,>0 能存活的时间秒   -1  会话结束销毁
				 */
				//设置cookie 的生命周期
				cookie.setMaxAge(7*24*3600);
				System.out.println("我正在登陆了");
				//设置二级域名获取cookie信息
				cookie.setDomain("jt.com");
				//设置cookie 的权限   "/"  表示域名下的所有页面
				cookie.setPath("/");			
				//利用respnse对象将cookie写入客户端
				response.addCookie(cookie);
				System.out.println("我要快登陆了");
				return SysResult.ok();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return SysResult.fail();
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest requsest,HttpServletResponse response) {
		Cookie[] cookies = requsest.getCookies();
		if(cookies.length!=0) {
			String token=null;
			for (Cookie cookie : cookies) {
				
				String cookiename = cookie.getName();
				if("JT_TICKET".equals(cookiename)) {
					cookie.setMaxAge(0);
					cookie.setDomain("jt.com");
					cookie.setPath("/");
					token = cookie.getValue();
					if(!StringUtils.isEmpty(token)) {
						userService.deleteToken(token);
						response.addCookie(cookie);
					}
				
				}
				
			}
			
		}
		
		//这个是转发
		//return "index";
		//当用户登出时,页面重定向到系统首页
				return "redirect:/";
		
	}
	
	
//	@RequestMapping("/logout")//
//	public String logout2(HttpServletRequest requsest,HttpServletResponse response) {
//		
//		
//		
//		//当用户登出时,页面重定向到系统首页
//		return "redirect:/";
//		
//	}
//	
	
	

}
