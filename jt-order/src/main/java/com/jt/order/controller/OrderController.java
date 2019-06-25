package com.jt.order.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
import com.jt.order.vo.SysResult;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ObjectMapper objectMapper;
	
	//订单信息新增，同时入库三张表
	@RequestMapping("/create")
	public SysResult saveOrder(String orderJSON) {
		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			String orderId = orderService.saveOrder(order);
			if (!StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单入库失败");
	}
	
	@RequestMapping("/query/{orderId}")
	public Order findOrderById(@PathVariable String orderId) {
		return orderService.findOrderById(orderId);
	}
}
