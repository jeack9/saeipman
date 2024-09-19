package com.saeipman.app.payment.service;

import java.util.List;

public interface PaymentService {
	
	//기간 단건조회
	public List<PaymentVO> selectMonthInfo(PaymentVO payVO);
	
	//납부한 결제건 납부상태 변경
	public int updatePaymentInfo (int paymentYN);
	
}
