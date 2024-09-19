package com.saeipman.app.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.member.dto.MemberRequestDTO;
import com.saeipman.app.member.mapper.MemberMapper;
import com.saeipman.app.member.service.ImchainVO;
import com.saeipman.app.member.service.ImdaeinVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.MemberService;
import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RoomVO;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper lmapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RoomMapper rmapper;

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
		
		lvo.setPw(passwordEncoder.encode(dto.getPw()));
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
	
	// 로그인정보 단건추가 기본적으로 권한은 임대인 1로 이후 업데이트
	// 현재 로그인정보만 들어감
	@Transactional
	@Override
	public void addLogin(MemberRequestDTO dto) {
		String loginId = dto.getId();
		String pw = dto.getPw();
		
		// 중복체크
		if(lmapper.existsByLogin(loginId) >= 1) {
			return;
		}
		//임대인정보 추가
		ImdaeinVO imdaein = new ImdaeinVO();
		imdaein.setImdaeinId(dto.getId());
		imdaein.setImdaeinName(dto.getName());
		imdaein.setImdaeinEmail(dto.getEmail());
		imdaein.setPhone(dto.getPhone());
		imdaein.setBirth(dto.getBirth());
		lmapper.insertImdaein(imdaein);
		
		//로그인정보 추가
		LoginInfoVO joinVO = new LoginInfoVO();
		joinVO.setLoginId(loginId);
		joinVO.setPw(passwordEncoder.encode(pw));
		joinVO.setAuth(1);
		
		lmapper.insertLogin(joinVO);
		
	}
	@Override
	public boolean existsByLogin(String loginId) {
		return lmapper.existsByLogin(loginId) == 1;
	}
	// 임차인 단건추가 -- 로그인정보 추가
	@Override
	@Transactional
	public boolean addImchain(ConstractVO constractVO) {
		String id = constractVO.getImchainPhone();
		int duplicateLogin = lmapper.existsByLogin(id);
		if(duplicateLogin == 1) {
			lmapper.deleteImchain(id);
			lmapper.deleteLogin(id);
		}
		// 임차인 단건추가
		ImchainVO imchainVO = new ImchainVO();
		RoomVO roomVO = rmapper.selectRoomInfo(constractVO.getRoomId()); 
		imchainVO.setImchainId(id);
		imchainVO.setImchainName(constractVO.getImchainName());
		imchainVO.setRoomNo(roomVO.getRoomNo());
		int result = lmapper.insertImchain(imchainVO);
		if(result == 1) {
			// 임차인 단건추가 성공 - 로그인정보 단건추가
			LoginInfoVO loginVO = new LoginInfoVO();
			loginVO.setLoginId(id);
			loginVO.setPw(passwordEncoder.encode(constractVO.getImchainAccount()));
			loginVO.setAuth(2);
			lmapper.insertLogin(loginVO);
			return true;
		}
		return false;
	}
	@Override
	public void removeImchain(String imchainId) {
		lmapper.deleteImchain(imchainId);
	}
	@Override
	public void removeLogin(String id) {
		lmapper.deleteLogin(id);
	}
	
	

}
