package com.saeipman.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class TestController {
	@GetMapping("/")
	public String test() {
		return "test";
	}
	
	@GetMapping("/api/test")
	@ResponseBody
	public String getMethodName() {
		return "test";
	}
	
}
