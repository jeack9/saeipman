package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import com.saeipman.app.gwanlibi.service.GwanlibiDetailsVO;

public interface GwanlibiDetailsMapper {
	
//	public List<GwanlibiDetailsVO> selectGwanlibiList(String buildingId, String selectedDate);
	public List<GwanlibiDetailsVO> selectGwanlibiList(String buildingId);
	
}