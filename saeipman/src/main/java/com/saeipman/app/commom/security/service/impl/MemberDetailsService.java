//
//package com.saeipman.app.commom.security.service.impl;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.saeipman.app.commom.security.service.MemberDetails;
//import com.saeipman.app.member.service.LoginInfoVO;
//import com.saeipman.app.member.service.LoginService;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//
//@RequiredArgsConstructor
//public class MemberDetailsService implements UserDetailsService {
//	private final LoginService loginService;
//
//	@Override
//	public UserDetails loadUserByUsername(String insertUserId) throws UsernameNotFoundException {
//		LoginInfoVO loginVO = new LoginInfoVO();
//		loginVO.setLoginId(insertUserId);
//		LoginInfoVO findVO = loginService.loginInfo(loginVO);
//		if (findVO == null) {
//			throw new UsernameNotFoundException("아이디 혹은 비밀번호가 올바르지 않습니다.");
//		}
//
//		return new MemberDetails(findVO);
//	}
//}
