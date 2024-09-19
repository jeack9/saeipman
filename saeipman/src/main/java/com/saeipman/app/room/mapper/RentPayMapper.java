package com.saeipman.app.room.mapper;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.room.service.RentPayVO;

public interface RentPayMapper {
	// 월세납부내역 단건등록 상태는 0(대기)로 무조건 등록
	public int insertRentPayHistory(RentPayVO rentPayVO);
	
	// 월세납부내역 상태변경
	public int updatePayStateByRentPayNo(@Param("mRentHistoryNo") String rentPayNo, @Param("paymentState") int paymentState);
}
