package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;


@Service(timeout=3000)
public class CartServiceImpl implements DubboCartService {
	@Autowired
	private CartMapper CartMapper;

	@Override
	public List<Cart> findCartListById(Long userId) {
		QueryWrapper<Cart> queryWrapper = new  QueryWrapper<Cart>();
		queryWrapper.eq("user_id", userId);
		
		List<Cart> list = CartMapper.selectList(queryWrapper);
		return list;
	}

	@Override
	@Transactional
	public void updateCartNum(Cart cart) {
		
		//更新数据信息num/updated
		Cart tempCart=new Cart();
		tempCart.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<Cart>();
		updateWrapper.eq("user_id", cart.getUserId());
		updateWrapper.eq("item_id", cart.getItemId());
		CartMapper.update(tempCart, updateWrapper);
	
		
	}
	
	/**
	 * 条件构造器中将对象中不为null的属性当作where条件   前提是:保证有cart两个属性不为null
	 */

	@Override
	@Transactional
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>(cart);
		
		CartMapper.delete(queryWrapper);
		
	}
/**
 * 新增业务的实现 
 * 1 第一次的新增可以直接入库
 * 2 
 */
	@Override
	@Transactional
	public void insertCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
		queryWrapper.eq("user_id", cart.getUserId());
		queryWrapper.eq("item_id", cart.getItemId());
		Cart cartDB = CartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			//第一次商品入库
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			CartMapper.insert(cart);
		}else {
			//表示用户之前有加过商品
			int num=cart.getNum()+cartDB.getNum();
			cartDB.setNum(num).setUpdated(new Date());
			CartMapper.updateById(cartDB);
		}
		
		
		
		
	} 

	


}
