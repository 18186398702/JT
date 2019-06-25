package com.jt.web.service;

import java.util.List;

import com.jt.common.po.Cart;

public interface CartService {

	List<Cart> findCartLIstByUserId(Long userId);

	void updateCart(Cart cart);

	void saveCart(Cart cart);

	void deleteCart(Long userId, Long itemId);
	
}
