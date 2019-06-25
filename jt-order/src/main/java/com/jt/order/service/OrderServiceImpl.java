package com.jt.order.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Override
	public String saveOrder(Order order) {
		String orderId = ""+ order.getUserId() + System.currentTimeMillis();
		Date date = new Date();
		//1.实现订单入库
		order.setOrderId(orderId);
		order.setStatus(1); //1表示未付款
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功");
		// 订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单信息入库成功");
		return orderId;
	}

	@Override
	public Order findOrderById(String orderId) {
		Order order = orderMapper.selectById(orderId);
		OrderShipping orderShipping = orderShippingMapper.selectById(orderId);
		List<OrderItem> lists = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
		order.setOrderShipping(orderShipping);
		order.setOrderItems(lists);
		return order;
	}

	//用户订单超时修改状态
	@Override
	public void updateOrderStatus() {
		//实例化日历对象
		Calendar calendar = Calendar.getInstance();
		// 当前时间-30分钟
		calendar.add(Calendar.MINUTE, -30);
		Date timeOut = calendar.getTime();
		Order order = new Order();
		order.setStatus(6);
		order.setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1);
		updateWrapper.lt("created", timeOut);
		orderMapper.update(order, updateWrapper);
	}

}
