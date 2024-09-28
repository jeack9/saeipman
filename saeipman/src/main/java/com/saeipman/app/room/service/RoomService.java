package com.saeipman.app.room.service;

import java.util.List;

import com.saeipman.app.commom.paging.PagingDTO;

public interface RoomService {
	// 임차인 아이디 -> 방, 계약 정보조회
	public RoomVO imchainRoomInfo(String imchainId);
	
	// 건물 선택 -> 방목록 조회
	public List<BuildingRoom> buildingRoomList(BuildingRoom buildingRoom, PagingDTO paging);
	// 페이징용 건물 방수 조회
	public int totalBuildingRoomFilter(BuildingRoom buildingRoom);
	
	// 건물 방 수 조회
	public int totalBuildingRoom(BuildingRoom buildingRoom);
	// 건물 입주 방수 조회
	public int buildingIpjuCount(BuildingRoom buildingRoom);
	// 방 단건조회 방아이디 -> 방 조회
	public RoomVO roomInfo(String roomId);
	
	// 방 아이디 -> 계약목록 페이징
	public List<ConstractVO> roomConstractsPaging(String roomId, PagingDTO paging);
	// 방 아이디 -> 계약목록 토탈
	public int roomConstractTotal(String roomId);
	
	// 방 입주상태 변경
	public boolean modiIpjuState(RoomVO roomVO);
}
