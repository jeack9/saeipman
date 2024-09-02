package com.saeipman.app.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginService;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class LoginController {
	private LoginService lsvc;
	
	@GetMapping("member/login")
	public void loginForm() {};
	
	@GetMapping("member/join")
	public void joinForm() {};
	
	@PostMapping("login")
	@ResponseBody
	public String login(LoginInfoVO loginVO) {
		LoginInfoVO login = lsvc.loginInfo(loginVO);
		boolean isLogin = login == null ? false : true;
		if(isLogin) return "성공";
		else return "실패";
	}
	
}
