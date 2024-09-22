package com.saeipman.app.room.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.room.mapper.ConstractMapper;
import com.saeipman.app.room.mapper.RoomMapper;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RoomVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConstractServiceImpl implements ConstractService{
	private final ConstractMapper cmapper;
	private final RoomMapper rmapper;
	@Override
	public ConstractVO constractInfoImchain(String imcahinId) {
		return cmapper.selectConstractImchain(imcahinId);
	}
	@Override
	public ConstractVO constractInfo(String constractNo) {
		return cmapper.selectConstractInfo(constractNo);
	}
	
	@Override
	public ConstractVO nextConstractInfoByRoomId(String roomId) {
		return cmapper.selectNextConstractInfoByRoomId(roomId);
	}
	@Override
	public ConstractVO currentConstractInfoByRoomId(String roomId) {
		return cmapper.selectCurrentConstractInfoByRoomId(roomId);
	}
	@Override
	@Transactional
	public String addConstract(ConstractVO constractVO) {
		int result = cmapper.insertConstractInfo(constractVO);
		// 계약 추가 방 입실 상태 변경
		if(result == 1) {
			String newConstractNo = constractVO.getConstractNo();
			RoomVO updateRoom = new RoomVO();
			if(constractVO.getConstractState() == 1) {
				updateRoom.setIpjuState(1);
				updateRoom.setRoomId(constractVO.getRoomId());
				rmapper.updateIpjuState(updateRoom);
			}
			return newConstractNo;
		}
		return "-1";
	}
	@Override
	@Transactional
	public ConstractVO modiConstract(ConstractVO constractVO) {
		int result = cmapper.updateConstractInfo(constractVO);
		if(result == 1) {
			// 업데이트 후 방의 계약상태 확인
			ConstractVO currentConstract = cmapper.selectCurrentConstractInfoByRoomId(constractVO.getRoomId());
			RoomVO updateRoom = new RoomVO();
			
			if(currentConstract != null) {
				// 방의 현재계약이 있는 경우 입주상태 입주처리
				updateRoom.setIpjuState(1);
			}else {
				updateRoom.setIpjuState(-1);
			}
			updateRoom.setRoomId(constractVO.getRoomId());
			rmapper.updateIpjuState(updateRoom);
			return constractVO;
		}
		return null;
	}
	@Override
	public List<Map<String, Object>> roomConstractList(String buildingId, PagingDTO paging) {
		return cmapper.selectRoomConstract(buildingId, paging);
	}
	
	
	@Override
	public int roomConstractTotal(String buildingId) {
		return cmapper.roomConstractTotal(buildingId);
	}

}
