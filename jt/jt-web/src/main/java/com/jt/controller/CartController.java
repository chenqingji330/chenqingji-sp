package com.jt.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Reference(timeout=3000,check=false)
	private  DubboCartService cartService;
	
	/**
	 * 实现商品列表的展示
	 */
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		//User user = (User)request.getAttribute("JT_USER");
		User user = UserThreadLocal.get();
		Long userId=user.getId();//暂时写死
		List<Cart> cartList=cartService.findCartListById(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
		
	}
	/**
	 * 如果url参数使用restFul风格获取参数时,如果和pojo相匹配时可以使用pojo接收
	 * @param cart
	 * @return
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		try {
			User user = UserThreadLocal.get();
			Long userId=user.getId();//暂时写死
			cart.setUserId(userId);
			cartService.updateCartNum(cart);
			return SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
			
		}
		
	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
	
		User user = UserThreadLocal.get();
		Long userId=user.getId();//暂时写死
			cart.setUserId(userId);
			cartService.deleteCart(cart);
			
			return "redirect:/cart/show.html";
		
	}
	
	/**
	 * 新增购物车
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		User user = UserThreadLocal.get();
		Long userId=user.getId();//暂时写死
		cart.setUserId(userId);
		cartService.insertCart(cart);
		
		//新增数据之后展现购物车列表信息
		
		return "redirect:/cart/show.html";
		
	}

}
