package com.saeipman.app.gwanlibi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public int addtItems(List<GwanlibiVO> vo) {
		return gwanlibiMapper.insertItems(vo);
	}
	
	// 관리비 상세 내역
	@Override
	public List<GwanlibiVO> detailsBillList(GwanlibiVO vo) {
		
		System.err.println(vo.getPaymentMonth());
		
		if (vo.getPaymentMonth() == null) {
			// 현재 날짜를 VO에 담아주기.
			Date now = new Date();
			System.err.println(now);
			vo.setPaymentMonth(now);
			System.err.println(vo.getPaymentMonth());
		}
		
		return gwanlibiMapper.selectGwanlibiDetailsBill(vo);
	}
	
	// 관리비 등록
	@Override
	public Map<String, Object> addMaintenanceCoast(List<GwanlibiVO> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 등록 관리비 한 건씩 넣어주기.
		for(GwanlibiVO vo : list) {
			gwanlibiMapper.insertMaintenanceCost(vo);
		}
		
		map.put("result", true);
		map.put("vo", list);
		
		return map;
	}

	
}