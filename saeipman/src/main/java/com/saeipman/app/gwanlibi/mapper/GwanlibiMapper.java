package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.gwanlibi.service.GaguGwanlibiHistoryVO;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

public interface GwanlibiMapper {
	// 건물 리스트
	//public List<GwanlibiVO> selectMonthGwanlibiByBuildingList(String imdaeinId, BuildingPageDTO dto);
	public List<GwanlibiVO> selectMonthGwanlibiByBuildingList(@Param("imdaeinId") String imdaeinId, @Param("dto") BuildingPageDTO dto);
	
	// 페이징 - 건물 총 개수
	public int getBuildingTotalCount(String imdaeinId);
	
	
	// 기본 관리비 목록
	public List<GwanlibiVO> selectBasicGwanlibiList();
	
	// 관리비 항목 리스트
	public List<GwanlibiVO> selectItemList(String buildingId);
	
	// 관리비 항목 버전
	public int selectUpdateVesion(String buildingId);
	
	// 관리비 항목 등록
	public int insertItems(GwanlibiVO vo);
	
	// 사용자의 건물별 관리비 상세 내역
	public List<GwanlibiVO> selectGwanlibiDetailsBill(GwanlibiVO vo);
	
	// 정산한 관리비 등록
	public int insertGwanlibi(@Param("monthGwanlibiInfo") GwanlibiVO monthGwanlibiInfo, @Param("list") List<GwanlibiVO> list, @Param("gaguGwanlibiHistoryList") List<GaguGwanlibiHistoryVO> gList);
	
	// 정산한 관리비 수정
	public int updateGwanlibiDetails(List<GwanlibiVO> list);
	public int updateMonthGwanlibi(GwanlibiVO gwanlibiVO);
	
	// 각 건물별 입주 총 가구 정보
	public List<GwanlibiVO> selectTotalGagu(String buildingId);
	// 각 건물별 총 가구수
	public int countTotalGagu(String buildingId);
	// 각 건물별 입주한 총 가구수
	public int countIpjuTotalGagu(String buildingId);
	
	// 월 관리비 데이터 수
	public int getCountingMonthGwanlibiData(String buildingId);
	
	// 건물별 방 아이디 - 가구별 관리비 납부 내역에 필요
	public List<GaguGwanlibiHistoryVO> selectRoomIdList(String buildingId);
	

}
