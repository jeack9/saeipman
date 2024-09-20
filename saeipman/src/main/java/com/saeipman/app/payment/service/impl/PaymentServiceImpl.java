package com.saeipman.app.payment.service.impl;

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
	public List<PaymentVO> selectMonthInfo(PaymentVO payVO) {
		return paymentMapper.selectMonthPay(payVO);
	}


	@Override
	@Transactional
	public int updatePaymentStatus(PaymentVO payVO) {
		paymentMapper.updateGwanlibiStatus(payVO);
		//paymentMapper.updateMonthRentStatus(payVO);
		return 1;
	}



}
