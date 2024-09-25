package com.saeipman.app.main.service;

import com.saeipman.app.payment.service.PaymentVO;

public interface MainService {

	// 계약자 카운팅
	public int selectConstractState(PaymentVO payVO);

	// 미납자 카운팅
	public int unPaymentState(PaymentVO payVO);
}
