package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import com.saeipman.app.gwanlibi.service.GwanlibiPaymentVO;

public interface GwanlibiPaymentMapper {
	
	// 호실별 관리비 납부 현황
	public List<GwanlibiPaymentVO> selectGwanlibiPaymentStateList(String buildingId);

}
