package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import com.saeipman.app.gwanlibi.service.GwanlibiVO;


public interface GwanlibiMapper {
	// 사용자의 건물별 관리비 목록
	public List<GwanlibiVO> selectMonthGwanlibiByBuildingList(String imdaeinId);
	
	
	// 사용자의 건물별 관리비 상세 내역
	public List<GwanlibiVO> selectGwanlibiDetailsBill(GwanlibiVO vo);
	
}
