package com.saeipman.app.commom.security;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;

public class SecurityUtil {
	public static LoginInfoVO getLoginInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return customUser.getMember();
	}
	public static String getLoginId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return customUser.getUsername();
	}
	public static List<GrantedAuthority> getLoginAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return (List<GrantedAuthority>) customUser.getAuthorities();
	}
}
