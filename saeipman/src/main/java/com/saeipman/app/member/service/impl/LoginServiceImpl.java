package com.saeipman.app.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

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

	@Override
	public Map<String, Object> imdaeinInfo(String id) { // 임대인 단건조회
		Map<String, Object> map = new HashMap<String, Object>();
		ImdaeinVO imdaein = lmapper.selectImdaeinInfo(id);
		boolean hasImdaein = imdaein == null ? false : true;
		map.put("result", hasImdaein);
		map.put("imdaein", imdaein);
		return map;
	}

	@Override
	public void addIdaein(ImdaeinVO imdaeinVO) { // 임대인 단건추가

	}

	@Override
	public boolean checkImdaein(String id) {
		// 회원가입 아이디 중복체크
		ImdaeinVO imdaein = lmapper.selectImdaeinInfo(id);
		boolean hasId = imdaein == null ? false : true;
		return hasId;
	}

}
