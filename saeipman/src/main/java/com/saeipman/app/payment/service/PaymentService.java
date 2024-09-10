package com.saeipman.app.payment.service;

public interface PaymentService {
	
	//기간 단건조회
	public PaymentVO selectMonthInfo(PaymentVO payVO);
	
	//관리비 납부내역
	public PaymentVO selectPaymentHistory(PaymentVO payVO);
	
	//관리비 조회
	public PaymentVO payAmountInfo(PaymentVO payVO);
	
	
}
