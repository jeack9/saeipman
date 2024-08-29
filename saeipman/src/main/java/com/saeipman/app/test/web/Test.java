package com.saeipman.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
	@GetMapping("index")
	public String testindex() {
		return "board/index";
	}
	
}
