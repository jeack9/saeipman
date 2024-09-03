package com.saeipman.app.commom.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class WebControllerAdvice {
	@ModelAttribute("contextPath")
	public String contextPath(final HttpServletRequest request) {
		return request.getServletContext().getContextPath();
	}
}
