package com.saeipman.app.payment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<PaymentVO> selectMonthInfo(PaymentVO payVO) {
		return paymentMapper.selectMonthPay(payVO);
	}

//	@Override
//	public PaymentVO selectPaymentHistory(PaymentVO payVO) {
//		return paymentMapper.PayHistory(payVO);
//	}
//
//	@Override
//	public PaymentVO payAmountInfo(PaymentVO payVO) {
//		return paymentMapper.PayAmount(payVO);
//	}

}
