package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name="tb_item")  //对象与表一一映射
@JsonIgnoreProperties(ignoreUnknown=true)  //进行Json转化时忽略未知属性
public class Item extends BasePojo{
	//el表达式取值 一般都调用对象的get方法
	//前台展示商品图片的时候有 ${item.images[0] }
	
 	@Id  //表示主键
 	@GeneratedValue(strategy=GenerationType.IDENTITY)  //表示自增
	private Long    id ;       //商品的ID
	private String  title ;
	private String  sellPoint;
	private Long    price ;   //6190 / 100 
	private Integer num ;     //商品的数量
	private String  barcode ; //条码的信息
	private String  image ;   //保存图片信息 ， 多张图片采用“,”分割 1.jpg,2.jpg
	private Long    cid ;     //商品分类的id号
	private Integer status ;  //状态信息1正常 2下架  3删除
	
	/**
	 * 由于前台需要用get方法获取第一张图片的信息
	 * 所以手动添加一个get方法
	 * @return
	 */
	public String[] getImages(){
		return image.split(",");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
