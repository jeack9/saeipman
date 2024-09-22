package com.saeipman.app.find.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saeipman.app.find.mapper.FindMapper;
import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;

@Service
public class FindServiceImpl implements FindService{
	@Autowired
	private FindMapper findMapper;
	 @Autowired
	    private PasswordEncoder passwordEncoder;  // PasswordEncoder를 주입받음
	
	@Override
	public FindVO idSelect(FindVO findVO) {
		return findMapper.selectId(findVO);
	}

	@Override
	public FindVO pwSelect(FindVO findVO) {
		return findMapper.selectPw(findVO);
	}

	@Override
	public FindVO imchainPw(FindVO findVO) {
		return findMapper.imchainPw(findVO);
	}

	@Override
	public int pwUpdate(FindVO findVO) {
		//비밀번호 암호화 처리
		String pw = passwordEncoder.encode(findVO.getPw());
		findVO.setPw(pw); //암호화된 비밀번호로 설정
		
		// 업데이트 실행
        int update = findMapper.updatePw(findVO);  
        return update;  
		
	
	}



}
