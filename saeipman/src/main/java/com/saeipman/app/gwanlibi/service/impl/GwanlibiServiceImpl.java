package com.saeipman.app.gwanlibi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.mapper.GwanlibiMapper;
import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GwanlibiServiceImpl implements GwanlibiService {

	private GwanlibiMapper gwanlibiMapper;

	@Override
	public List<GwanlibiVO> monthGwanlibiByBuildingList(String imdaeinId) {
		return gwanlibiMapper.selectMonthGwanlibiByBuildingList(imdaeinId);
	}

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