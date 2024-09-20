package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import com.saeipman.app.gwanlibi.service.GwanlibiMsgVO;

public interface GwanlibiMsgMapper {
	
	// 해당 건물에 입주한 임차인 연락처 조회
	public List<GwanlibiMsgVO> selectImcahinPhoneNumber(String buildingId);
	
	// 현재 로그인 되어있는 임대인 연락처 조회
	public String selectImdaeinPhoneNumber(String imdaeinId);

}