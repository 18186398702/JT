package com.jt.web.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private HttpClientService httpClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		String md5PassWord = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		params.put("password", md5PassWord);
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		// 发送请求获取回执
		String result = httpClient.doPost(url, params);
		// 获取数据进行校验
		try {
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			if (sysResult.getStatus() != 200) {
				System.out.println("后台执行错误" + result);
				throw new RuntimeException();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public String findUserByUP(User user) {
		String token = null;
		String url = "http://sso.jt.com/user/login";
		Map<String, String> params = new HashMap<>();
		params.put("username", user.getUsername());
		//密码加密处理,必须与注册时的加密算法一致
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		params.put("password", md5Pass);
		String result = httpClient.doPost(url, params);
		try {
			SysResult sysResult = objectMapper.readValue(result, SysResult.class);
			if (sysResult.getStatus()==200) {
				token = (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
