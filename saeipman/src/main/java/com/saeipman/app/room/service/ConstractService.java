package com.saeipman.app.room.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.commom.paging.PagingDTO;

public interface ConstractService {
	// 임차인아이디 - 계약단건 조회
	public ConstractVO constractInfoImchain(String imcahinId);
	
	// 계약번호 - 계약단건 조회
	public ConstractVO constractInfo(String constractNo);
	
	// 방 - 다음계약 조회
	public ConstractVO nextConstractInfoByRoomId(String roomId);
	
	// 방 - 현재계약 조회
	public ConstractVO currentConstractInfoByRoomId(String roomId);
	
	// 계약단건 등록 - 등록된 계약번호 반환(쓸지는모름)
	public String addConstract(ConstractVO constractVO);
	
	// 계약단건 수정 - 수정된 계약정보 반환
	public ConstractVO modiConstract(ConstractVO constractVO);
	
	// 건물선택 - 방들의 현재계약 리스트
	public List<Map<String, Object>> roomConstractList(String buildingId, PagingDTO paging);
	
	// 건물선택 - 방계약 목록 totla
	public int roomConstractTotal(String buildingId);
	
	// 활성화된 계약 중 연락처으로 중복검색
	public boolean existsByPhoneActive(String imchainPhone);
	
	// 방의 이전 계약정보 조회
	public ConstractVO prevConstractByRoomId(String roomId);
	
}
