
package com.saeipman.app.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.service.LoginService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class LoginJoinAPI {

	private final LoginService loginService;

	@PostMapping("join")
	public String joinProc(MemberRequestDTO memberRequestDTO) {
		loginService.addLogin(memberRequestDTO);
		return "ok";
	}

}
