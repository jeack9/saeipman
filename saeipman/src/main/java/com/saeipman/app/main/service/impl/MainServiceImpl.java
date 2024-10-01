package com.saeipman.app.main.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;


import com.saeipman.app.main.mapper.MainMapper;
import com.saeipman.app.main.service.MainService;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonVO;
import com.saeipman.app.payment.service.PaymentVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
	private final MainMapper mainMapper;

	@Override
	public int selectConstractState(PaymentVO payVO) {
		return mainMapper.selectConstract(payVO);
	}

	@Override
	public List<PaymentVO> unPaymentState(PaymentVO paymentVO) {
		return mainMapper.selectUnPayment(paymentVO);

	}
	@Override
	public int getTotalRoom(String imdaeinId) {
		return mainMapper.selectTotalRoom(imdaeinId);
	}
	@Override
	public int getExpCnt() {
		return mainMapper.selectExpCnt();
	}
	@Override
	public List<MinwonVO> minwonListMain(String imdaeinId) {
		return mainMapper.mainMinwonList(imdaeinId);
	}
}
