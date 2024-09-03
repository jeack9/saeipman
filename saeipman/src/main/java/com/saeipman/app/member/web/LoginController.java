package com.saeipman.app.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("login") // 로그인폼 이동
	public void loginForm() {};
	
	@GetMapping("join") // 회원가입폼 이동
	public void joinForm() {};
	
	@PostMapping("login") // 로그인처리
	@ResponseBody
	public String login(LoginInfoVO loginVO) {
		LoginInfoVO login = lsvc.loginInfo(loginVO);
		boolean isLogin = login == null ? false : true;
		if(isLogin) return "성공";
		else return "실패";
	}
	@PostMapping("join") // 회원가입처리
	public String join(ImdaeinVO imdaeinVO, @RequestParam(name = "pw") String pw) {
		Map<String, Object> map = lsvc.imdaeinInfo(imdaeinVO.getImaeinId());
		if((boolean) map.get("result")) {
			
		}
		return "";
	}
	@PostMapping("ckId")
	@ResponseBody
	public boolean checkId(@RequestParam(name = "id") String id) {
		// 아이디 중복체크
		boolean hasId = lsvc.checkImdaein(id);
		return hasId;
	}
	
}
