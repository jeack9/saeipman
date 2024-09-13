package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.gwanlibi.mapper.GwanlibiMapper;
import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GwanlibiServiceImpl implements GwanlibiService {

	private GwanlibiMapper gwanlibiMapper;
	
	// 건물 리스트 출력 - 전월, 금월 관리비, 페이징 처리
	@Override
	public List<GwanlibiVO> monthGwanlibiByBuildingList(@Param("imdaeinId") String imdaeinId, @Param("dto") BuildingPageDTO dto) {
		return gwanlibiMapper.selectMonthGwanlibiByBuildingList(imdaeinId, dto);
	}
	
	// 건물 총 개수
	@Override
	public int buildingTotalCount(String imdaeinId) {
		return gwanlibiMapper.getBuildingTotalCount(imdaeinId);
	}
	
	// 기본 관리비 항목 리스트
	@Override
	public List<GwanlibiVO> basicGwanlibiList() {
		return gwanlibiMapper.selectBasicGwanlibiList();
	}
	
	// 관리비 항목 리스트
	@Override
	public List<GwanlibiVO> itemList(String buildingId) {
		return gwanlibiMapper.selectItemList(buildingId);
	}
	
	// 관리비 항목 버전
	@Override
	public int getUpdateVesion(String buildingId) {
		return gwanlibiMapper.selectUpdateVesion(buildingId);
	}
	
	// 관리비 항목 등록
	@Override
	public void addtItems(List<GwanlibiVO> list) {
		for(GwanlibiVO item : list) {
			gwanlibiMapper.insertItems(item);
		}
	}
	
	// 관리비 상세 내역
	@Override
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo) {
		return gwanlibiMapper.selectGwanlibiDetailsBill(vo);
	}


	// 정산한 관리비 등록 todo
	@Override
	public void addGwanlibi(List<GwanlibiVO> list) {
		// 매퍼에서 받을 값을 monthGwanlibiInfo에 담아서 보내주기.
		GwanlibiVO monthGwanlibiInfo = new GwanlibiVO();
		
		// month_gwanlibi - 관리비 총 금액 계산.
		int total = 0;
		String buildingId = "";
		int gwanlibiMoney = 0;
		for(GwanlibiVO ele : list) {
			total += ele.getFixedPrice();
			buildingId = ele.getBuildingId();
			//gwanlibiMoney = ele.getFixedPrice();
		}
		System.err.println(total);
		monthGwanlibiInfo.setTotalMoney(total);
		
		// building_id 세팅.
		monthGwanlibiInfo.setBuildingId(buildingId);
		
		// 고정 관리비 -> 관리미 항목 별 금액
		//monthGwanlibiInfo.setGwanlibiItemMoney(gwanlibiMoney);		
		
		// month_gwanlibi - 가구별 관리비 계산.
		int totalGagu = gwanlibiMapper.selectToTalGagu(buildingId);
		int gwanlibiByGagu = total / totalGagu;
		monthGwanlibiInfo.setGaguGwanlibi(gwanlibiByGagu);

		gwanlibiMapper.insertGwanlibi(monthGwanlibiInfo, list);
	}
	
	// 총 가구수
	@Override
	public int getToTalGagu(String buildingId) {
		int result = gwanlibiMapper.selectToTalGagu(buildingId);
		return result;
	}
	
}