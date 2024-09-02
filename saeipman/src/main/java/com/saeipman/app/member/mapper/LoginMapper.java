package com.saeipman.app.member.mapper;


import com.saeipman.app.member.service.LoginInfoVO;


public interface LoginMapper {
	// 단건 조회
	public LoginInfoVO selectLoginInfo(LoginInfoVO loginVO);
	
}
