package com.saeipman.app.member.service;

import java.util.Map;

import org.springframework.validation.Errors;

public interface LoginService {
	// 로그인정보 단건 조회
	public LoginInfoVO loginInfo(LoginInfoVO loginVO);
	
	// 임대인정보 단건 조회
	public Map<String, Object> imdaeinInfo(String id);
	
	// 임대인 아이디 중복체크
	public boolean checkImdaein(String id);
	
	// 임대인정보 단건 추가
	public void addIdaein(ImdaeinVO imdaeinVO);


}