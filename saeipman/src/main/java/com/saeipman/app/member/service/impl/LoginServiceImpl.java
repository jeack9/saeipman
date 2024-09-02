package com.saeipman.app.member.service.impl;

import org.springframework.stereotype.Service;

import com.saeipman.app.member.mapper.LoginMapper;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService{

	private LoginMapper lmapper;
	
	@Override
	public LoginInfoVO loginInfo(LoginInfoVO loginVO) {
		LoginInfoVO login = lmapper.selectLoginInfo(loginVO);
		return login;
	}

}
