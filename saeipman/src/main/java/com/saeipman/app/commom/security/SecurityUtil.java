package com.saeipman.app.commom.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;

public class SecurityUtil {
	public static LoginInfoVO getLoginInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() == null) {
			return null;
		}
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return customUser.getMember();
	}
	public static String getLoginId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() == null) {
			return null;
		}
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		return customUser.getUsername();
	}
	public static int getLoginAuth() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() == null) {
			return -1;
		}
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		LoginInfoVO loginSession = customUser.getMember();
		return loginSession.getAuth();
	}
	public static String getRoomId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() == null) {
			return null;
		}
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		LoginInfoVO loginSession = customUser.getMember();
		return loginSession.getRoomId();
	}
	public static String getBuildingId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() == null) {
			return null;
		}
		CustomUserDetails customUser = (CustomUserDetails)authentication.getPrincipal();
		LoginInfoVO loginSession = customUser.getMember();
		return loginSession.getBuildingId();
	}
}
