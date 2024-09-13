
package com.saeipman.app.member.service;

import java.util.Map;
import com.saeipman.app.member.dto.MemberRequestDTO;

public interface MemberService {
//	   로그인정보 단건 조회 
	public LoginInfoVO loginInfo(LoginInfoVO loginVO);

	// 임대인정보 단건 조회
	public Map<String, Object> imdaeinInfo(String id);

	// 임대인 아이디 중복체크
	public boolean checkImdaein(String id);

	// 임대인 로그인정보 단건 추가
	public int addImdaein(MemberRequestDTO dto);

	// 로그인정보 추가
	public void addLogin(MemberRequestDTO dto);

	// 로그인정보 중복체크
	public boolean existsByLogin(String loginId);
}
