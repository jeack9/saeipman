package com.saeipman.app.room.service;

public interface RoomService {
	// 임차인 아이디 -> 방, 계약 정보조회
	public RoomVO imchainRoomInfo(String imchainId);
}
