package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	//实现用户页面跳转
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}
	
	//(优先匹配准确路径的)实现用户退出操作 1.删除cookie 2.删除redis
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if ("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break ;
			}
		}
		//删除redis
		jedisCluster.del(token);
		//删除cookie
		Cookie newCookie = new Cookie("JT_TICKET", "");
		newCookie.setMaxAge(0);
		newCookie.setPath("/");
		response.addCookie(newCookie);
		return "redirect:/index.html";
	}
	
	//SpringMVC可以使用对象接受参数
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK(); //表示程序执行成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户注册失败");
	}
	
	// 登录url： /service/user/doLogin
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		try {
			//调用业务层方法返回用户登录业务的token数据
			String token = userService.findUserByUP(user);
			//判断token不为空时,在客户端存储cookie
			if (!StringUtils.isEmpty(token)) {
				Cookie tokencookie = new Cookie("JT_TICKET", token);
				tokencookie.setMaxAge(7*24*3600);//表示token数据存7天
				//cookie使用权限配置，表示当前网站的任何路径下都可以访问到这个cookie
				tokencookie.setPath("/");
				response.addCookie(tokencookie);
				return SysResult.oK(); //此return必须放在if里面
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败");
	}
}
