/*
 * package com.saeipman.app.commom.security.service.impl;
 * 
 * import org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.stereotype.Service;
 * 
 * import com.saeipman.app.commom.security.service.CustomUserDetails; import
 * com.saeipman.app.member.service.LoginInfoVO; import
 * com.saeipman.app.member.service.LoginService;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @Service
 * 
 * @RequiredArgsConstructor public class CustomUserDetailsService implements
 * UserDetailsService { private final LoginService loginService;
 * 
 * @Override public UserDetails loadUserByUsername(String username) throws
 * UsernameNotFoundException { LoginInfoVO loginVO = new LoginInfoVO();
 * loginVO.setLoginId(username); LoginInfoVO findVO =
 * loginService.loginInfo(loginVO); if(findVO == null) { throw new
 * UsernameNotFoundException("없는 아이디"); } return new CustomUserDetails(findVO);
 * }
 * 
 * }
 */