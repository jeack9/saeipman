package com.saeipman.app.room.service.impl;

import org.springframework.stereotype.Service;

import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.RoomService;
import com.saeipman.app.room.service.RoomVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
	private final RoomMapper roomMapper;
	@Override
	public RoomVO imchainRoomInfo(String imchainId) {
		return roomMapper.selectImchainRoom(imchainId);
	}

}
