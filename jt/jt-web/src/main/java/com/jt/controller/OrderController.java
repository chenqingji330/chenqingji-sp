package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderServiece;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Reference(timeout=3000,check=false)
	private  DubboOrderServiece orderService;
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	
	
	//Request URL: http://www.jt.com/order/create.html
	@RequestMapping("/create")
	public String OrderCreate(Model model) {
		User user = UserThreadLocal.get();
		List<Cart> carts=cartService.findCartListById(user.getId());
		model.addAttribute("carts", carts);
		
		return "order-cart";
		
	}
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult insertOrder(Order order) {
		
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			String orderId=orderService.insertOrder(order);
			if(!StringUtils.isEmpty(orderId)) {
				
				return SysResult.ok(orderId);
			}
			
		
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		return SysResult.fail();
	}
	
	//http://www.jt.com/order/success.html?id=141560929188074
	
	//根据订单的信息查询数据
	@RequestMapping("/success")
	public  String findOrderById(String id,Model model) {
		
	  Order	order =orderService.findOrderById(id);
	  model.addAttribute("order", order);
		return "success";
		
	}

}
