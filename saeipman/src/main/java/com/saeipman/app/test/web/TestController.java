package com.saeipman.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.commom.security.SecurityUtil;



@Controller
public class TestController {
	@GetMapping("/")
	public String test() {
		int auth = SecurityUtil.getLoginAuth();
		String url = auth == 1 ? "redirect:/member/home" : "redirect:/paymentInfo";
		return url;
	}
	
	@GetMapping("/api/test")
	@ResponseBody
	public String getMethodName() {
		return "test";
	}
	
}
