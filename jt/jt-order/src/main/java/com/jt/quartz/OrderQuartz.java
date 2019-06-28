package com.jt.quartz;



import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;



//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**当用户订单提交30分钟后,如果还没有支付.状态改为6,则交易关闭
	 * sql语句:update tb_order set status =6 ,updated=#{date} where status=1 and created<now-30
	 * 现在时间 - 订单创建时间 > 30分钟  则超时
	 * new date - 30 分钟 > 订单创建时间
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//设定30分钟超时
		Calendar calendar = Calendar.getInstance();//获取当前时间
		calendar.add(Calendar.MINUTE, -30);
		Date date = calendar.getTime();
		Order order = new Order();
		order.setStatus(6);
		order.setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", "1").lt("created",date);
		orderMapper.update(order, updateWrapper);
		System.out.println("定时任务执行成功");
	}
}