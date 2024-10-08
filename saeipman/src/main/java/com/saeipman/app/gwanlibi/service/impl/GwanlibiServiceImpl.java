package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.gwanlibi.mapper.GwanlibiMapper;
import com.saeipman.app.gwanlibi.service.GaguGwanlibiHistoryVO;
import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GwanlibiServiceImpl implements GwanlibiService {

	private GwanlibiMapper gwanlibiMapper;
	
	
	// 건물 리스트 출력 - 전월, 금월 관리비, 페이징 처리
	@Override
	public List<GwanlibiVO> monthGwanlibiByBuildingList(@Param("imdaeinId") String imdaeinId,
													    @Param("dto") BuildingPageDTO dto) {
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


	// 정산한 관리비 등록
	@Override
	public void addGwanlibi(List<GwanlibiVO> list, List<GaguGwanlibiHistoryVO> roomIdlist) {
		// 매퍼에서 받을 값을 monthGwanlibiInfo에 담아서 보내주기.
		GwanlibiVO monthGwanlibiInfo = new GwanlibiVO();
		
		// month_gwanlibi - 관리비 총 금액 계산.
		int total = 0;
		//String buildingId = "";
		for(GwanlibiVO ele : list) {
			total += ele.getFixedPrice();
			//buildingId = ele.getBuildingId();
		}
		System.err.println("납부일********" + list.get(0).getPaymentMonth());
		monthGwanlibiInfo.setTotalMoney(total);
		
		// building_id 세팅.
		monthGwanlibiInfo.setBuildingId(list.get(0).getBuildingId());
		monthGwanlibiInfo.setPaymentMonth(list.get(0).getPaymentMonth());		
		
		// 고정 관리비 -> 관리미 항목 별 금액
		//monthGwanlibiInfo.setGwanlibiItemMoney(gwanlibiMoney);
		
		// month_gwanlibi - 가구별 관리비 계산.
		List<GwanlibiVO> totalGagu = gwanlibiMapper.selectTotalGagu(list.get(0).getBuildingId());
		int gwanlibiByGagu = total / totalGagu.size();
		monthGwanlibiInfo.setGaguGwanlibi(gwanlibiByGagu);
		System.err.println("입주한 총 가구수 : " + totalGagu.size());
		System.err.println(gwanlibiByGagu);

		gwanlibiMapper.insertGwanlibi(monthGwanlibiInfo, list, roomIdlist);
	}
	
	
	// 월 관리비 데이터 개수
	@Override
	public int getCountingMonthGwanlibiData(String buildingId) {
		return gwanlibiMapper.getCountingMonthGwanlibiData(buildingId);
	}

	// 관리비 수정
	@Override
	public void modifyGwanlibi(List<GwanlibiVO> gridDatalist) {
		System.err.println(gridDatalist);
		// updateGwanlibiDetails
		gwanlibiMapper.updateGwanlibiDetails(gridDatalist);
		
		// month_gwanlibi update setting
		GwanlibiVO gwanlibiVO = new GwanlibiVO();
		int totalMoney = 0;
		String buildingId = "";
		for(GwanlibiVO gwanlibi : gridDatalist) {
			buildingId = gwanlibi.getBuildingId();
			gwanlibiVO.setPaymentMonth(gwanlibi.getPaymentMonth());
			totalMoney += gwanlibi.getGwanlibiItemMoney();
		}
		gwanlibiVO.setBuildingId(buildingId);
		gwanlibiVO.setTotalMoney(totalMoney);
		
		// 가구별 관리비
		List<GwanlibiVO> totalGagu = gwanlibiMapper.selectTotalGagu(buildingId);
		System.err.println("총 가구 수 : " + totalGagu);
		int gwanlibiByGagu = totalMoney / totalGagu.size();
		gwanlibiVO.setGaguGwanlibi(gwanlibiByGagu);	
		
		
		System.err.println(gwanlibiVO);
		// updateMonthGwanlibi
		gwanlibiMapper.updateMonthGwanlibi(gwanlibiVO);
	}

	// 방 아이디 리스트
	@Override
	public List<GaguGwanlibiHistoryVO> roomIdList(String buildingId) {
		return gwanlibiMapper.selectRoomIdList(buildingId);
	}
	
}