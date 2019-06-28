package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service(timeout=3000)
public class DubboOrderServiceImpl implements DubboOrderServiece {
	@Autowired
	private  OrderItemMapper orderItemMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	/**
	 * 一个业务逻辑操作三张数据表
	 * 1添加事务
	 * 2表数据分析 order orderItems orderShipping
	 * 3准备orderId  :订单号:用户id+当前时间戳
	 * 4操作三个mapper分别入库
	 */
	@Override
	@Transactional
	public String insertOrder(Order order) {
		String orderId=order.getUserId()+""+System.currentTimeMillis();
		Date date = new Date();
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		order.setOrderId(orderId).setStatus(1).setCreated(date).setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功!");
		
		//3入库订单物流
		OrderShipping shipping = order.getOrderShipping();
		shipping.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("订单物流入库成功!!");
		
		//4入库订单商品
		  List<OrderItem> orderItems = order.getOrderItems();
		  for (OrderItem orderItem : orderItems) {
			  orderItem.setOrderId(orderId).setCreated(date).setUpdated(date);
			  
			  orderItemMapper.insert(orderItem);
		}
		 System.out.println("订单商品入库成功!!"); 
	
		return orderId;
	}
	
@Override
public Order findOrderById(String id) {
	Order order = orderMapper.selectById(id);
	OrderShipping shipping = orderShippingMapper.selectById(id);
	QueryWrapper<OrderItem> queryWrapper = new  QueryWrapper<OrderItem>();
	queryWrapper.eq("order_id", id);
	List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
	order.setOrderItems(list).setOrderShipping(shipping);
	
	return order;
}
}
