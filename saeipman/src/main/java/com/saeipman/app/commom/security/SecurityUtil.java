package com.saeipman.app.commom.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;

public class SecurityUtil {
	public static LoginInfoVO getLoginInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return customUser.getMember();
	}
}
