package com.saeipman.app.member.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class LoginController {
	private final LoginService lsvc;

	@GetMapping("login") // 로그인폼 이동
	public void loginForm() {};

	@GetMapping("join") // 회원가입폼 이동
	public void joinForm(Model model) {
		model.addAttribute("memberReq", new MemberRequestDTO());
	};
	@GetMapping("home")
	public void homeP() {}
	

	@PostMapping("login") // 로그인처리
	@ResponseBody
	public String login(LoginInfoVO loginVO) {
		LoginInfoVO login = lsvc.loginInfo(loginVO);
		boolean isLogin = login == null ? false : true;
		if (isLogin)
			return "성공";
		else
			return "실패";
	}

	@PostMapping("join") // 회원가입처리
	public String join(ImdaeinVO imdaeinVO, @RequestParam(name = "pw") String pw) {
		Map<String, Object> map = lsvc.imdaeinInfo(imdaeinVO.getImdaeinId());
		if ((boolean) map.get("result")) {

		}
		return "";
	}
	
	// 아이디 중복체크
	@PostMapping("ckId")
	@ResponseBody
	public boolean checkId(@RequestParam(name = "id") String id) {
		boolean hasId = lsvc.checkImdaein(id);
		return hasId;
	}
	
	// 회원가입 폼 유효성검사
	@PostMapping("/vaildJoin")
	public String validJoin(@Valid @ModelAttribute("memberReq") MemberRequestDTO memberReq, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			System.out.println("bbbb");
			return "member/join";
		}
		System.out.println("aaaa");
		lsvc.addImdaein(memberReq);
		return "member/login";
	}
	
	// jwt 테스트
	@GetMapping("/test")
	public String testP() {
		return "test";
	}
	
}
