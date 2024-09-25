package com.saeipman.app.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.main.mapper.MainMapper;
import com.saeipman.app.main.service.MainService;
import com.saeipman.app.payment.service.PaymentVO;

@Service
public class MainServiceImpl implements MainService {
	private MainMapper mainMapper;

	@Autowired
	public MainServiceImpl(MainMapper mainMapper) {
		this.mainMapper = mainMapper;
	}

	@Override
	public int selectConstractState(PaymentVO payVO) {
		return mainMapper.selectConstract(payVO);
	}

	@Override
	public int unPaymentState(PaymentVO payVO) {
		return mainMapper.selectUnPayment(payVO);

	}

}
