package com.saeipman.app.gwanlibi.service;

import java.util.List;

import com.saeipman.app.building.service.BuildingPageDTO;

public interface GwanlibiService {
	// 건물 리스트
	public List<GwanlibiVO> monthGwanlibiByBuildingList(String imdaeinId, BuildingPageDTO dto);
	
	// 페이징 - 건물 총 개수
	public int buildingTotalCount(String imdaeinId);
	
	// 기본 관리비 항목 리스트
	public List<GwanlibiVO> basicGwanlibiList();
	
	// 관리비 항목 리스트
	public List<GwanlibiVO> itemList(String buildingId);
	
	// 관리비 항목 버전
	public int getUpdateVesion(String buildingId);
	
	// 관리비 항목 등록
	public void addtItems(List<GwanlibiVO> vo);
	
	// 사용자의 건물별 관리비 상세 내역
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo);
	
	// 정산한 관리비 등록
	public void addGwanlibi(List<GwanlibiVO> item, List<GaguGwanlibiHistoryVO> roomIdList);
	// 정산한 관리비 수정
	public void modifyGwanlibi(List<GwanlibiVO> gridDatalist);
	
	
	// 월 관리비 데이터 수
	public int getCountingMonthGwanlibiData(String buildingId);
	
	// 건물별 방 아이디 - 가구별 관리비 납부 내역에 필요
	public List<GaguGwanlibiHistoryVO> roomIdList(String buildingId);
}
