package com.saeipman.app.main.service;


import java.sql.Date;
import java.util.List;

import com.saeipman.app.payment.service.PaymentVO;

public interface MainService {

	// 계약자 카운팅
	public int selectConstractState(PaymentVO payVO);
	// 미납자 카운팅
	public List<PaymentVO> unPaymentState(PaymentVO paymentVO);
	
	public int getTotalRoom(String imdaeinId);
	
	public int getExpCnt();
}
