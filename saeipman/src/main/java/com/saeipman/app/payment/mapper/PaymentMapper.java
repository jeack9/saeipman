package com.saeipman.app.payment.mapper;

import java.util.List;

import com.saeipman.app.payment.service.PaymentVO;

public interface PaymentMapper {

	
	// 기간 조회(관리비) 선택한 월만 단건조회로 가져오기
	public List<PaymentVO> selectMonthPay(PaymentVO payVO);
	
	//납부한 결제건 납부상태 변경
	public int updatePayment(int paymentYN);

	
}
