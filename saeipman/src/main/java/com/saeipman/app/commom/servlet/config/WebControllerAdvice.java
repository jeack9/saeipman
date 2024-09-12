package com.saeipman.app.commom.servlet.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.member.service.LoginInfoVO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class WebControllerAdvice {
	@ModelAttribute("contextPath")
	public String contextPath(final HttpServletRequest request) {
		return request.getServletContext().getContextPath();
	}
	@ModelAttribute("login")
	public LoginInfoVO loginSession() {
		return SecurityUtil.getLoginInfo();
	}
}
