package com.jt.manage.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.jt.manage.pojo.User;
public interface UserMapper {
	
	//��ѯ�û����е�����
	List<User> findAll();
}
