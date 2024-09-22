package com.saeipman.app.gwanlibi.service;

import java.util.List;

public interface GwanlibiPaymentService {
	
	// 관리비 납부 내역 리스트
	public List<GwanlibiPaymentVO> getGwanlibiPaymentStateList(String buildingId);

}
