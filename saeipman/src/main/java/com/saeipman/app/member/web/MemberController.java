package com.saeipman.app.member.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("all")
public class MemberController {
	private final MemberService lsvc;
	
	// 로그인폼 이동
	@GetMapping("login") 
	public void loginP() {};

	// 회원가입폼 이동
	@GetMapping("join") 
	public void joinP(Model model) {
		model.addAttribute("memberReq", new MemberRequestDTO());
	};
	
	// 메인페이지
	@GetMapping("home") 
	public void homeP() {}

	@PostMapping("join") // 회원가입처리
	public String join(ImdaeinVO imdaeinVO, @RequestParam(name = "pw") String pw) {
		Map<String, Object> map = lsvc.imdaeinInfo(imdaeinVO.getImdaeinId());
		if ((boolean) map.get("result")) {

		}
		return "";
	}

	// 아이디 중복체크
	@GetMapping("ckId/{id}")
	@ResponseBody
	public Map<String, Object> checkId(@PathVariable(name = "id") String id) {
		System.out.println("중복체크중");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean hasId = lsvc.existsByLogin(id);
		map.put("hasId", hasId);
		map.put("id", id);
		return map;
	}

	// 회원가입 폼 유효성검사
	@PostMapping("/vaildJoin")
	public String validJoin(@Valid @ModelAttribute("memberReq") MemberRequestDTO memberReq, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "all/join";
		}
		lsvc.addImdaein(memberReq);
		return "all/login";
	}
}