package com.jt.manage.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.manage.pojo.User;

@RestController
public class JSONPController {

	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		User user = new User();
		user.setId(100);
		user.setName("tom");
		JSONPObject obj = new JSONPObject(callback, user);
		return obj;
	}
}
