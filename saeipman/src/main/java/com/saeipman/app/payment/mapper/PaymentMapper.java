package com.saeipman.app.payment.mapper;

import com.saeipman.app.payment.service.PaymentVO;

public interface PaymentMapper {

	
	// 기간 조회(관리비) 선택한 월만 단건조회로 가져오기
	public PaymentVO selectMonthPay(PaymentVO payVO);

//	// 해당 기간의 관리비 납부내역 조회(가구 관리비 내역 테이블 조인, 납부내역, 관리비 번호)
//	public PaymentVO PayHistory(PaymentVO payVO);

//	// 납부해야할 관리비 조회(미납금 포함)
//	public PaymentVO PayAmount(PaymentVO payVO);

	//
}
