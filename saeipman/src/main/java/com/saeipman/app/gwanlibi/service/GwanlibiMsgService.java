package com.saeipman.app.gwanlibi.service;

import java.util.List;

public interface GwanlibiMsgService {
	
	// 해당 건물에 입주한 임차인 연락처 조회
	public List<GwanlibiMsgVO> getImchainPhoneNumber(String buildingId);
	
	public List<GwanlibiMsgVO> getGwanlibiOverdueImchainList(String buildingId);
	
	
	
	// 현재 로그인 되어있는 임대인 연락처 조회
	//public GwanlibiMsgVO getImdaeinPhoneNumber(String imdaeinId);
	public String getImdaeinPhoneNumber(String imdaeinId);
}
