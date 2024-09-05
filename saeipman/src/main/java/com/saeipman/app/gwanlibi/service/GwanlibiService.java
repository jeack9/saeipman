package com.saeipman.app.gwanlibi.service;

import java.util.List;

public interface GwanlibiService {
	// 건물별 월 관리비
	public List<GwanlibiVO> monthGwanlibiByBuildingList(String imdaeinId);
	
	//
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo);

}
