package com.saeipman.app.gwanlibi.mapper;

import java.util.List;


import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;


public interface GwanlibiMapper {
	// 건물 리스트
	public List<GwanlibiVO> selectMonthGwanlibiByBuildingList(String imdaeinId, BuildingPageDTO dto);
	
	// 페이징 - 건물 총 개수
	public int getBuildingTotalCount(String imdaeinId);
	
	// 관리비 항목 리스트
	public List<GwanlibiVO> selectItemList(String buildingId);
	
	// 사용자의 건물별 관리비 상세 내역
	public List<GwanlibiVO> selectGwanlibiDetailsBill(GwanlibiVO vo);
	
	
	// 관리비 등록
	public int insertMaintenanceCost(GwanlibiVO vo);
	
	
	
}
