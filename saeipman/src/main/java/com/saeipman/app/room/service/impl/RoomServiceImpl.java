package com.saeipman.app.room.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.BuildingRoom;
import com.saeipman.app.room.service.ConstractVO;
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
	@Override
	public List<BuildingRoom> buildingRoomList(BuildingRoom buildingRoom, PagingDTO paging) {
		return roomMapper.selectBuildingRoom(buildingRoom, paging);
	}
	@Override
	public int totalBuildingRoom(BuildingRoom buildingRoom) {
		return roomMapper.totalBuildingRoom(buildingRoom);
	}
	@Override
	public int buildingIpjuCount(BuildingRoom buildingRoom) {
		return roomMapper.buildingIpjuCount(buildingRoom);
	}
	@Override
	public RoomVO roomInfo(String roomId) {
		return roomMapper.selectRoomInfo(roomId);
	}
	@Override
	public boolean modiIpjuState(RoomVO roomVO) {
		return roomMapper.updateIpjuState(roomVO) == 1;
	}
	@Override
	public int roomConstractTotal(String roomId) {
		return roomMapper.roomConstractTotal(roomId);
	}
	@Override
	public List<ConstractVO> roomConstractsPaging(String roomId, PagingDTO paging) {
		return roomMapper.roomConstractsPaging(roomId, paging);
	}
	@Override
	public int totalBuildingRoomFilter(BuildingRoom buildingRoom) {
		return roomMapper.totalBuildingRoomFilter(buildingRoom);
	}

}
