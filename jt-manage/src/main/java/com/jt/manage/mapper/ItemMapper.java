package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Item;

public interface ItemMapper extends SysMapper<Item>{
	
	@Select("select count(*) from tb_item")
	int findItemCount();
	
	/**
	 * 规定：
	 * mybaits中不允许多值传参
	 * 核心思想：将多值转化为单值
	 * 实际应用：1.	对象  2.Map集合(@Param原理其实就是)3.List/Array
	 * @param count 
	 * @param start 
	 * @return
	 */
	//@Select("select * from tb_item order by updated desc limit #{start} , #{count}")
	List<Item> findItemList(@Param("start") Integer start,@Param("rows") Integer rows);
	
	@Select("select name from tb_item_cat where id = #{itemId}")
	String findItemCatNameById(Long itemId);

	void updateStatus(@Param("ids")Long[] ids, @Param("status")int status);
	
}	
