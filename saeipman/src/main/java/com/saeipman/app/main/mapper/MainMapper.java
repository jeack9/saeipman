package com.saeipman.app.main.mapper;

import java.util.List;

import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonVO;
import com.saeipman.app.payment.service.PaymentVO;

public interface MainMapper {

	// 계약자 카운팅
	public int selectConstract(PaymentVO payVO);

	// 미납자 카운팅
	public List<PaymentVO> selectUnPayment(PaymentVO paymentVO);
	
	public int selectTotalRoom(String imdaeinId);
	
	public int selectExpCnt();
	
	//민원
	public List<MinwonVO> mainMinwonList(Criteria cri);
}
