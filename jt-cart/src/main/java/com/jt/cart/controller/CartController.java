package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.cart.util.MapperUtil;
import com.jt.cart.vo.SysResult;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/query/{userId}")
	public SysResult findCartListByUserId(@PathVariable Long userId) {
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		return SysResult.oK(cartList);
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}") 
	//restful风格单个参数必须加@Pathvarible,对象可以不加
	public SysResult updateCartNum(Cart cart) {
		try {
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "修改购物车数量失败");
	}
	
	//后台保存减价购物车 
	@RequestMapping("/save")
	public SysResult saveCart(String cartJSON) {
		try {
			System.out.println(cartJSON);
			Cart cart = MapperUtil.toObject(cartJSON, Cart.class);
			System.out.println(cart.getItemTitle());
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车新增失败");
	}
	
	@RequestMapping("/delete/{userId}/{itemId}") 
	public SysResult deleteCart(Cart cart) {
		try {
			cartService.deleteCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除购物车商品失败");
	}
	
}
