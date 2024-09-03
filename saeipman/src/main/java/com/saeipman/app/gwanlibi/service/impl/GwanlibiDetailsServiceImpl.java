package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.mapper.GwanlibiDetailsMapper;
import com.saeipman.app.gwanlibi.service.GwanlibiDetailsService;
import com.saeipman.app.gwanlibi.service.GwanlibiDetailsVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GwanlibiDetailsServiceImpl implements GwanlibiDetailsService {
	private GwanlibiDetailsMapper gwanlibiDetailsMapper;

	@Override
	public List<GwanlibiDetailsVO> detailsList(String buildingId) {
		return gwanlibiDetailsMapper.selectGwanlibiList(buildingId);
	}
//	public List<GwanlibiDetailsVO> detailsList(String buildingId, String selectedDate) {
//		return gwanlibiDetailsMapper.selectGwanlibiList(buildingId, selectedDate);
//	}

}
