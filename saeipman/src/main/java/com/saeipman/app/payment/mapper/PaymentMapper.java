package com.saeipman.app.payment.mapper;

import com.saeipman.app.payment.service.PaymentVO;

public interface PaymentMapper {

	// 기간 조회(관리비)
	public PaymentVO selectMonth(PaymentVO payVO);

	// 해당 기간의 관리비 납부내역 조회(가구 관리비 내역 테이블 조인, 납부내역, 관리비 번호)
	public String selectPay(PaymentVO payVO);

	// 납부해야할 관리비 조회
	public int selectPayment(PaymentVO payVO);

	//
}
