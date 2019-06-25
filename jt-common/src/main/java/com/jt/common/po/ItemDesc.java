package com.jt.common.po;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{
	@Id
	private Long itemId ;  //与商品id相同
	private String itemDesc ; //商品详情html
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public ItemDesc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ItemDesc(Long itemId, String itemDesc) {
		super();
		this.itemId = itemId;
		this.itemDesc = itemDesc;
	}
	
	
}
