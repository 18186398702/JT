package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	/**
	 * 分页查询数据信息
	 * 1.查询记录总数
	 * 2.查询商品信息
	 */
	@Override
	public EasyUIResult findItemListByPage(Integer page, Integer rows) {
//		int count = itemMapper.findItemCount();
		//利用通用mapper获得count
		int count = itemMapper.selectCount(null);
		int start = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemList(start,rows);
		
		return new EasyUIResult(count,itemList);
	}
	@Override
	public String findItemCatNameById(Long itemId) {
		return itemMapper.findItemCatNameById(itemId);
	}
	
	/**
	 * 通用Mapper
	 * 数据库查询原理
	 * insert into  .... 执行完后
	 * select last_insert_id()
	 */
	@Override
	public void saveItem(Item item,String desc) {
		//新增商品
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//新增商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());	//获取item添加后回传id
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);                             
	}
	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids,status);
	}
	@Override
	public ItemDesc findItemDesc(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
	@Override
	public void updateItem(Item item, String desc) {
		//更改修改商品时间
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		//修改商品详情
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}
	@Override
	public void deleteItems(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}
	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}
}
