package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.po.User;
import com.jt.common.util.MapperUtil;
import com.jt.web.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	private JedisCluster jedisCluster;

	// 调用controller执行业务之前
	/**
	 * 判断用户是否登录，先获取cookie，在获取token，再查询redis中是否存在
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(token)) {
			String userJSON = jedisCluster.get(token);
			if (!StringUtils.isEmpty(userJSON)) {
				User user = MapperUtil.toObject(userJSON, User.class);
				//request.setAttribute("JT_USER", user);
				//使用ThreadLocal代替保存数据
				UserThreadLocal.set(user);
				return true;
			}
		}
		// 重定向到用户登录页面
		response.sendRedirect("/user/login.html");
		return false; // false表示拦截，true表示放行
	}

	// controller方法执行结束后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}
	
	//视图渲染完成之后执行，一般用作关闭链接、关闭流，删除某些对象防止内存泄漏
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//controller方法执行完成后删除对象
		UserThreadLocal.remove();
	}

}
