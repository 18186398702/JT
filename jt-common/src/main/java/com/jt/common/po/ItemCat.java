package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_item_cat")
public class ItemCat extends BasePojo{
	@Id  //表示主键
 	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ; 		//商品分类ID
	private Long parentId ; //商品分类父级ID
	private String name ;   //商品分类名称
	private Integer status ;// 商品状态信息1.正常 2. 删除
	private Integer sortOrder; //定义排序号
	// 注意使用包装类型
	private Boolean isParent;  //是否为父级
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
