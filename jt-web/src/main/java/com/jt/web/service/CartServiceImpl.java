package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Cart;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.MapperUtil;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private HttpClientService httpClient;
	@Autowired
	private ObjectMapper objectMapper; //被封装了一次。MapperUtil
	
	//操作jt-cart
	@Override
	public List<Cart> findCartLIstByUserId(Long userId) {
		String url = "http://cart.jt.com/cart/query/"+userId;
		String result = httpClient.doGet(url);
		SysResult sysResult = MapperUtil.toObject(result,SysResult.class);
		List<Cart> cartList = (List<Cart>) sysResult.getData();
		return cartList;
	}
	
	
	@Override
	public void updateCart(Cart cart) {
		String url = "http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
		httpClient.doGet(url);
	}


	@Override
	public void saveCart(Cart cart) {
		String url = "http://cart.jt.com/cart/save";
		String cartJSON = MapperUtil.toJSON(cart);
		Map<String, String> params = new HashMap<>();
		params.put("cartJSON", cartJSON);
		httpClient.doPost(url, params);
	}


	@Override
	public void deleteCart(Long userId, Long itemId) {
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClient.doGet(url);
	}
	
}

