package com.saeipman.app.room.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.room.service.ConstractVO;

public interface ConstractMapper {
	// 임차인 아이디(연락처) - 계약단건 조회
	public ConstractVO selectConstractImchain(String imchainId);
	// 계약번호 - 계약단건 조회
	public ConstractVO selectConstractInfo(String constratNo);
	
	// roomId, 계약상태 - 현재계약 조회
	public ConstractVO selectCurrentConstractInfoByRoomId(String roomId);
	
	// roomId, 계약상태 - 대기계약 조회
	public ConstractVO selectNextConstractInfoByRoomId(String roomId);
	
	// 계약 단건등록 - 등록된 계약번호 반환
	public int insertConstractInfo(ConstractVO constractVO);
	
	// 계약 단건수정 - 수정된 계약정보 반환
	public int updateConstractInfo(ConstractVO constractVO);
	
	// 건물선택 - 건물의 현재계약 목록
	public List<Map<String,Object>> selectRoomConstract(@Param("buildingId")String buildingId, @Param("paging") PagingDTO paging);
	
	// 건물선택 - 건물의 현재계약 목록 토탈
	public int roomConstractTotal(String buildingId);
	
	// 임차인 중복체크 -- 활성화된 계약서 중 임차인 연락처 체크
	public int existsByPhoneActive(String imchainPhone);
	
	// 방의 이전 계약정보 조회
	public ConstractVO selectPrevConstractByRoomId(String roomId);
}
