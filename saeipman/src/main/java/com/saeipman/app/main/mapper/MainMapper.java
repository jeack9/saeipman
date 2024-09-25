package com.saeipman.app.main.mapper;

import com.saeipman.app.payment.service.PaymentVO;

public interface MainMapper {

	// 계약자 카운팅
	public Integer selectConstract(PaymentVO payVO);

	// 미납자 카운팅
	public Integer selectUnPayment(PaymentVO payVO);

}
