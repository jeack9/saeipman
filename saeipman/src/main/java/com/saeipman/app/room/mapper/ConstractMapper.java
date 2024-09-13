package com.saeipman.app.room.mapper;

import java.util.List;

import com.saeipman.app.room.service.ConstractVO;

public interface ConstractMapper {
	// 임차인 아이디(연락처) - 계약단건 조회
	public ConstractVO selectConstractImchain(String imchainId);
}
