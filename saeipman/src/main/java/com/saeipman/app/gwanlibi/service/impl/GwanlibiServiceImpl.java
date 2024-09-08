package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

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
		return gwanlibiMapper.selectGwanlibiDetailsBill(vo);
	}

}