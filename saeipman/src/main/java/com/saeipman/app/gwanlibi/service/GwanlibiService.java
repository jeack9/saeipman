package com.saeipman.app.gwanlibi.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.building.service.BuildingPageDTO;

public interface GwanlibiService {
	// 건물 리스트
	public List<GwanlibiVO> monthGwanlibiByBuildingList(String imdaeinId, BuildingPageDTO dto);
	
	// 페이징 - 건물 총 개수
	public int buildingTotalCount(String imdaeinId);
	
	// 관리비 항목 리스트
	public List<GwanlibiVO> itemList(String buildingId);
	
	// 관리비 항목 버전
	public int getUpdateVesion(String buildingId);
	
	// 관리비 항목 등록
	public void addtItems(List<GwanlibiVO> vo);
	
	// 사용자의 건물별 관리비 상세 내역
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo);
	
	// 관리비 등록
	public Map<String, Object> addMaintenanceCoast(List<GwanlibiVO> vo);

}
