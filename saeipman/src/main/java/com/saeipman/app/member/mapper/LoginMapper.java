package com.saeipman.app.member.mapper;


import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;


public interface LoginMapper {
	// 로그인 단건조회
	public LoginInfoVO selectLoginInfo(LoginInfoVO loginVO);
	
	// 임대인 단건조회
	public ImdaeinVO selectImdaeinInfo(String id);
	
	// 임대인 단건추가
	
	// 임대인 아이디 중복체크
	public int ckIdDupl(String id);
	
}
