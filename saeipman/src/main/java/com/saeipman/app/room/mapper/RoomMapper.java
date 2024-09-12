package com.saeipman.app.room.mapper;

import java.util.List;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.room.service.RoomVO;

public interface RoomMapper {
	// 목록조회
	public List<RoomVO> roomInfoList(BuildingVO vo); // 임대인 특정건물의 방 목록 조회
	// 단건조회
	
	// 단건등록
	
	// 단건삭제
	
}
