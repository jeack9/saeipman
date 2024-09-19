package com.saeipman.app.room.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.room.mapper.RentPayMapper;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RentPayService;
import com.saeipman.app.room.service.RentPayVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentPayServiceImpl implements RentPayService {
	private final RentPayMapper rentPayMapper;
	// 월세납부내역 단건등록
	@Override
	public String addRentPayHistory(RentPayVO rentPayVO) {
		if(rentPayMapper.insertRentPayHistory(rentPayVO) == 1) {
			return rentPayVO.getmRentHistoryNo();
		}
		return  null;
	}
	
	// 월세납부내역 상태변경
	@Override
	public boolean modiStateByRentPayNo(String rentPayNo, int newState) {
		return rentPayMapper.updatePayStateByRentPayNo(rentPayNo, newState) == 1;
	}

	// 계약등록시 첫 월세납부내역 추가
	@Transactional
	@Override
	public boolean addRentPayAfterConstract(ConstractVO constractVO) {
		RentPayVO rentPayVO = new RentPayVO();
		rentPayVO.setConstractNo(constractVO.getConstractNo());
		rentPayVO.setRoomId(constractVO.getRoomId());
		rentPayVO.setPaymentDate(constractVO.getConstractDate());
		rentPayVO.setRealPaymentDate(constractVO.getConstractDate());
		rentPayVO.setPaymentMoney(constractVO.getmRent());
		rentPayVO.setRealPaymentMoney(constractVO.getmRent());
		rentPayVO.setDepositorName(constractVO.getImchainName());
		String rentPayNo = addRentPayHistory(rentPayVO);
		boolean result = modiStateByRentPayNo(rentPayNo, 1);
		return result;
	}

}
