package com.saeipman.app.room.service.impl;

import org.springframework.stereotype.Service;

import com.saeipman.app.room.mapper.ConstractMapper;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.ConstractVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConstractServiceImpl implements ConstractService{
	private final ConstractMapper cmapper;
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
	public String addConstract(ConstractVO constractVO) {
		int result = cmapper.insertConstractInfo(constractVO);
		if(result == 1) {
			return constractVO.getConstractNo();
		}
		return "-1";
	}
	@Override
	public ConstractVO modiConstract(ConstractVO constractVO) {
		int result = cmapper.updateConstractInfo(constractVO);
		if(result == 1) {
			return constractVO;
		}
		return null;
	}

}
