package com.saeipman.app.main.mapper;

import com.saeipman.app.payment.service.PaymentVO;

public interface MainMapper {

	// 계약자 카운팅
	public int selectConstract(PaymentVO payVO);

	// 미납자 카운팅
	public int selectUnPayment(PaymentVO payVO);

}
