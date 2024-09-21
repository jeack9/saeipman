package com.saeipman.app.payment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.payment.mapper.PaymentMapper;
import com.saeipman.app.payment.service.PaymentService;
import com.saeipman.app.payment.service.PaymentVO;

@Service
public class PaymentServiceImpl implements PaymentService {
	private PaymentMapper paymentMapper;

	@Autowired
	public PaymentServiceImpl(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper;
	}

	@Override
	public List<PaymentVO> selectPayInfo(PaymentVO payVO) {
		
		List<PaymentVO> payList = new ArrayList<>();
		
		List<PaymentVO> gw = paymentMapper.selectGwanlibiPay(payVO);
		List<PaymentVO> mr = paymentMapper.selectMonthlyRentPay(payVO);
		
		payList.addAll(gw);
		payList.addAll(mr);
		
		System.out.println("관리비!!!!!!!!!!!!!!! " + gw); // 로그 추가
	    System.out.println("월세!!!!!!!!!!!!!!!!!!!!!!: " + mr); // 로그 추가
		
		
		return payList;

	}

	/*
	 * @Override public List<PaymentVO> selectMonthlyRentPay(PaymentVO payVO) {
	 * return paymentMapper.selectMonthlyRentPay(payVO); }
	 */

	@Override
	@Transactional
	public int updatePaymentStatus(PaymentVO payVO) {
		paymentMapper.updateGwanlibiStatus(payVO);
		//paymentMapper.updateMonthRentStatus(payVO);
		return 1;
	}

}
