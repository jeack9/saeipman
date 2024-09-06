package com.saeipman.app.member.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.commom.security.JwtUtil;
import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.mapper.LoginMapper;
import com.saeipman.app.member.service.AuthService;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{
	
	private final JwtUtil jwtUtill;
	private final PasswordEncoder encoder;
	private final LoginMapper loginMapper;
	
	@Override
	public String login(LoginRequest dto) {
		String id = dto.getLoginId();
		String pw = dto.getPw();
		LoginInfoVO loginVO = new LoginInfoVO();
		loginVO.setLoginId(id);
		
		LoginInfoVO findVO = loginMapper.selectLoginInfo(loginVO);
		if(findVO == null) {
			throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
		}
		if(!encoder.matches(pw, findVO.getPw())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
		}
		return jwtUtill.createAccessToken(findVO);
	}
	
}
