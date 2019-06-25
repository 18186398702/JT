package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.manage.service.ItemService;

@RestController  // 等于@Controller  +  @ResponseBody
@RequestMapping("/web/item")
public class WebItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	public Item findItemById(Long itemId) {
		System.out.println(666);
		return itemService.findItemById(itemId);
	}
	
	@RequestMapping("/findItemDescById")
	public ItemDesc findItemDescById(Long itemId) {
		System.out.println(666);
		return itemService.findItemDesc(itemId);
	}
}
