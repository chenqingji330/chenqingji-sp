package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
@Component//将拦截器交给spring管理
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private JedisCluster jedisCluster;
	
	
	/**
	 * 1.获取用户Cookie获取token数据
	 * 2.判断token中是否有数据
	 * 		false 表示没有登陆,则重定向到用户登陆页面
	 * 		
	 * 		true :表示用户之前登陆过		
	 * 			从redis中根据token获取userJSON,
	 * 			再次判断数据是否有数据
	 * 			
	 * 			false: 没有数据,则重定向到用户登陆页面
	 * 			true : 表示有数据,则程序予以放行.
	 */

	@Override //业务处理之前
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("开启拦截截了哈");
		String token=null;
		//1 获取Cookie信息
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token=cookie.getValue();
				break;
			}			
		}
		//判断token是否有效
		if(!StringUtils.isEmpty(token)) {
			String userJSON = jedisCluster.get(token);
			//缓存中有用户数据
			if(!StringUtils.isEmpty(userJSON)) {
				User user = ObjectMapperUtil.jsonToObject(User.class, userJSON);
				//将对象存到request对象中
				//request.setAttribute("JT_USER", user);
				//将对象存到session对象中,切记用完需要关闭session中
				//request.getSession().setAttribute("JT_USER", user);
				UserThreadLocal.set(user);
				
				
				return true;
				
			}	
		}
		response.sendRedirect("/user/login.html");
		return false;
	}
	@Override//业务处理完成后马上拦截
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override//视图渲染完成后拦截
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//request.getSession().removeAttribute("JT-USER");
		 UserThreadLocal.remove();
		
		
	}
	
}
