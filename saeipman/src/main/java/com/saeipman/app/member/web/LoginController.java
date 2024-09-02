package com.saeipman.app.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@AllArgsConstructor
@RequestMapping("member")
public class LoginController {
	private LoginService lsvc;
	
	@GetMapping("login")
	public void loginForm() {};
	
	@GetMapping("join")
	public void joinForm() {};
	
	@PostMapping("login")
	@ResponseBody
	public String login(LoginInfoVO loginVO) {
		LoginInfoVO login = lsvc.loginInfo(loginVO);
		boolean isLogin = login == null ? false : true;
		if(isLogin) return "성공";
		else return "실패";
	}
	@PostMapping("join")
	@ResponseBody
	public String join(@ModelAttribute ImdaeinVO imdaeinVO) {
		System.out.println(imdaeinVO.toString());
		return "";
	}
	
}
