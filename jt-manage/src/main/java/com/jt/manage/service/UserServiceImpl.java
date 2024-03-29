package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper mapper;
	
	@Override
	@Transactional()
	public List<User> findAll() {
		return mapper.findAll();
	}

}
