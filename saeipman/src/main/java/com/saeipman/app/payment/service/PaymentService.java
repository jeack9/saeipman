package com.saeipman.app.payment.service;

import java.util.List;

public interface PaymentService {

	// 납부내역 조회
	public List<PaymentVO> selectPayInfo(PaymentVO payVO);
	
	// 납부상태 변경 - 월세/관리비
	public int updatePaymentStatus(PaymentVO payVO);


}
