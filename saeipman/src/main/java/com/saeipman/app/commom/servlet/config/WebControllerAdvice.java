package com.saeipman.app.commom.servlet.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class WebControllerAdvice {
	private final MemberService memberService;
	
	@ModelAttribute("contextPath")
	public String contextPath(final HttpServletRequest request) {
		return request.getServletContext().getContextPath();
	}
	@ModelAttribute("login")
	public LoginInfoVO loginSession() {
		LoginInfoVO login = new LoginInfoVO();
		try {
			login = SecurityUtil.getLoginInfo();
		} catch (Exception e) {
			return login;
		}
		return login;
	}
	@ModelAttribute("memberNames")
	public String getMemberName() {
		LoginInfoVO login = SecurityUtil.getLoginInfo();
		String memberName = "비로그인";
		if(login != null) {
			memberName = memberService.getMemberName(login.getLoginId(), login.getAuth());
		}
		return memberName;
	}
}
