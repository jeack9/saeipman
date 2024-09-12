package com.saeipman.app.commom.servlet.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class WebControllerAdvice {
	@ModelAttribute("contextPath")
	public String contextPath(final HttpServletRequest request) {
		return request.getServletContext().getContextPath();
	}
	
}
