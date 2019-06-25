package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.po.Cart;
import com.jt.common.po.Order;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	//订单页面跳转
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = cartService.findCartLIstByUserId(userId);
		model.addAttribute("carts",cartList);
		return "order-cart";
	}
	
	//实现订单新增
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		Long userId = UserThreadLocal.get().getId();
		order.setUserId(userId);
		String orderId = orderService.saveOrder(order);
		if (!StringUtils.isEmpty(orderId)) {
			return SysResult.oK(orderId);
		}
		return SysResult.build(201, "订单新增失败");
	}
	
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		Order order = orderService.findOrderById(id);
		model.addAttribute("order",order);
		return "success" ;
	}
}
