package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;
import com.jt.manage.service.ItemServiceImpl;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	// 根据分页实现商品信息的查询
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemListByPage(Integer page, Integer rows) {
		return itemService.findItemListByPage(page, rows);
	}

	/**
	 * 根据商品的类别ID去查询类别名称并显示 spring4的时候 当返回值为String的时候，编码规则按ISO-8859-1的格式
	 * 当返回值为Object的时候，编码按UTF-8 spring5统一为UTF-8
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/cat/queryItemName", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId) {
		return itemService.findItemCatNameById(itemId);
	}

	/**
	 * 商品新增
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc) {
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SysResult.build(201, "新增商品失败");
	}

	// 商品下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids) {  //springmvc提供了只要传过来的串是以逗号隔开可以直接用数组接收，自动转成数组
		try {
			int status = 2;
			itemService.updateStatus(ids, status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品下架失败");
	}

	// 商品上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids) {   //springmvc提供了只要传过来的串是以逗号隔开可以直接用数组接收，自动转成数组
		try {
			int status = 1;
			itemService.updateStatus(ids, status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品上架失败");
	}
	
	//商品详情信息回显
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId ) {
		try {
			ItemDesc itemDesc = itemService.findItemDesc(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品查询失败");
	}
	
	// 商品信息的修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "修改商品失败");
	}
	
	//商品删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败");
	}
	
}
