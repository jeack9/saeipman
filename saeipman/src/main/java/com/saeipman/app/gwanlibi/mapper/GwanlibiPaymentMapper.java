package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.gwanlibi.service.GwanlibiPaymentVO;

public interface GwanlibiPaymentMapper {
	
	// 호실별 관리비 납부 현황
	public List<GwanlibiPaymentVO> selectGwanlibiPaymentStateList(@Param("buildingId") String buildingId, @Param("paymentMonth") String paymentMonth);

}
