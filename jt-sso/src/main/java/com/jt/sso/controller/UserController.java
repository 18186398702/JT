package com.jt.sso.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import com.jt.sso.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 实现用户信息的校验
	 * 验证用户信息是否存在
	 */
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(String callback,@PathVariable  String param, @PathVariable Integer type) {
		System.out.println("param="+param);
		boolean flag = userService.findCheckUser(param, type);
		SysResult result = SysResult.oK(flag);
		return new JSONPObject(callback, result);
	}

	// 注册url : http://sso.jt.com/user/register
	@RequestMapping("/register")
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户注册失败");
	}

	//用户登录进行校验用户信息
	@RequestMapping("/login")
	public SysResult findUserByUP(User user) {
		try {
			String token = userService.findUserByUP(user);
			if (!StringUtils.isEmpty(token)) {
				return SysResult.oK(token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败");
	}
	
	//通过跨域实现登录后用户信息的获取  http://sso.jt.com/user/query/
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(String callback,@PathVariable String token) {
		String userJSON = jedisCluster.get(token);
		SysResult sysResult = SysResult.oK(userJSON);
		return new JSONPObject(callback, sysResult);
	}
}
