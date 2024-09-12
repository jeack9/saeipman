package com.saeipman.app.room.mapper;

import java.util.List;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.room.service.RoomVO;

public interface RoomMapper {
	// 방 목록조회
	public List<RoomVO> roomInfoList(BuildingVO vo); // 임대인 특정건물의 방 목록 조회: buildingId, imdaeinId 필요
	// 방 단건조회
	
	// 방 단건등록
	
	// 방 단건삭제
	
	// 로그인한 임차인아이디 -> 임차인의 방, 계약목록 조회(최신순)
	public RoomVO selectImchainRoom(String imchainId);
}
