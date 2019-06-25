package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String index() {
		System.out.println("index");
		return "index";
	}
	
	/**
	 * 1.url中参数必须使用/分割  
	 * 2.requestMapper接受参数时，采用{参数名称}进行接收  /page/item-add
	 * 3.方法接收参数时，采用注解+参数名称形式获取数据     叫restful格式
	 */
	@RequestMapping("/page/{moduleName}")
	public String item_add(@PathVariable String moduleName) {
		return moduleName;
	}
	
}
