package com.saeipman.app.gwanlibi.service;

import java.util.List;
import java.util.Map;

public interface GwanlibiService {
	// 건물별 월 관리비
	public List<GwanlibiVO> monthGwanlibiByBuildingList(String imdaeinId);
	
	//
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo);
	
	// 관리비 등록
	public Map<String, Object> addMaintenanceCoast(List<GwanlibiVO> vo);

}
