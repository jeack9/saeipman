package com.saeipman.app.member.web;

import java.util.Map;

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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
@RequestMapping("member")
public class LoginController {
	private LoginService lsvc;

	@GetMapping("login") // 로그인폼 이동
	public void loginForm() {
	};

	@GetMapping("join") // 회원가입폼 이동
	public void joinForm(Model model) {
		model.addAttribute("memberReq", new MemberRequestDTO());
	};

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
		Map<String, Object> map = lsvc.imdaeinInfo(imdaeinVO.getImaeinId());
		if ((boolean) map.get("result")) {

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
	
	@PostMapping("/vaildJoinProc")
	public void validJoinProc(@Valid MemberRequestDTO memberReq, BindingResult result, Model model) {
		// 회원가입 폼 유효성검사
		System.out.println("왔다" + memberReq.toString());
		if(result.hasErrors()) {
			model.addAttribute("memberReq", memberReq);
			//return "member/join";
		}
		//return "";
	}
	
}
