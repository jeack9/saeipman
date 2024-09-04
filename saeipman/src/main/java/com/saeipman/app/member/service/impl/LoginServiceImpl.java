package com.saeipman.app.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.mapper.LoginMapper;
import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.LoginService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

	private LoginMapper lmapper;

	@Override
	public LoginInfoVO loginInfo(LoginInfoVO loginVO) {
		LoginInfoVO login = lmapper.selectLoginInfo(loginVO);
		return login;
	}
    // 임대인 단건조회
	@Override
	public Map<String, Object> imdaeinInfo(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		ImdaeinVO imdaein = lmapper.selectImdaeinInfo(id);
		boolean hasImdaein = imdaein == null ? false : true;
		map.put("result", hasImdaein);
		map.put("imdaein", imdaein);
		return map;
	}

	// 임대인 단건추가
	@Transactional
	@Override
	public int addImdaein(MemberRequestDTO dto) { 
		ImdaeinVO ivo = new ImdaeinVO();
		ivo.setImdaeinId(dto.getId());
		ivo.setImdaeinName(dto.getName());
		ivo.setImdaeinEmail(dto.getEmail());
		ivo.setPhone(dto.getPhone());
		ivo.setBirth(dto.getBirth());
		int result = lmapper.insertImdaein(ivo);
				
		LoginInfoVO lvo = new LoginInfoVO();
		lvo.setLoginId(dto.getId());
		lvo.setPw(dto.getPw());
		lvo.setAuth(1);
		result += lmapper.insertLogin(lvo);
		return result;
	}

	@Override
	public boolean checkImdaein(String id) {
		// 회원가입 아이디 중복체크
		int result = lmapper.ckIdDupl(id);
		return result == 1 ? true : false;
	}

}
