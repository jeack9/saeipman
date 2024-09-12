package com.saeipman.app.payment.service;

import java.util.List;

public interface PaymentService {
	
	//기간 단건조회
	public List<PaymentVO> selectMonthInfo(PaymentVO payVO);
	
//	//관리비 납부내역
//	public PaymentVO selectPaymentHistory(PaymentVO payVO);
//	
//	//관리비 조회
//	public PaymentVO payAmountInfo(PaymentVO payVO);
	
	
}
