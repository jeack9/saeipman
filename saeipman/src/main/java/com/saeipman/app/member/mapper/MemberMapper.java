package com.saeipman.app.member.mapper;


import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;


public interface MemberMapper {
	// 로그인 단건조회
	public LoginInfoVO selectLoginInfo(LoginInfoVO loginVO);
	
	// 임대인 단건조회
	public ImdaeinVO selectImdaeinInfo(String id);
	
	// 임대인 단건추가
	public int insertImdaein(ImdaeinVO vo);
	
	// 임대인 아이디 중복체크
	public int ckIdDupl(String id);
	
	// 로그인 정보 단건추가
	public int insertLogin(LoginInfoVO vo);
	
	// 로그인정보 중복확인
	public int existsByLogin(String loginId);
}
