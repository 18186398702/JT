package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// 实现购物车列表的展示
	@RequestMapping("/show")
	public String findCartList(Model model,HttpServletRequest request) {
		//User user = (User) request.getAttribute("JT_USER");
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartLit = cartService.findCartLIstByUserId(userId);
		model.addAttribute("cartList", cartLit);
		return "cart";
	}

	// 编辑前台controller
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num) {
		try {
			//User user = (User) request.getAttribute("JT_USER");
			Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户修改数量失败");
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		//User user = (User) request.getAttribute("JT_USER");
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId) {
		//User user = (User) request.getAttribute("JT_USER");
		Long userId = UserThreadLocal.get().getId();
		cartService.deleteCart(userId,itemId);
		return "redirect:/cart/show.html";
	}
}
