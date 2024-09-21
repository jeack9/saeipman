package com.saeipman.app.payment.mapper;

import java.util.List;

import com.saeipman.app.payment.service.PaymentVO;

public interface PaymentMapper {

	
	// 기간 조회(관리비)
	public List<PaymentVO> selectGwanlibiPay(PaymentVO payVO);
	
	// 기간 조회(월세)
	public List<PaymentVO> selectMonthlyRentPay(PaymentVO payVO);
	
	
	
	//납부상태 변경 - 관리비
	public int updateGwanlibiStatus(PaymentVO payVO);
	
	//납부상태 변경 - 월세
	public int updateMonthRentStatus(PaymentVO payVO);
	
}
