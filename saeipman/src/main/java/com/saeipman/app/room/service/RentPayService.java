package com.saeipman.app.room.service;

public interface RentPayService {
	// 월세납부내역 단건추가
	public String addRentPayHistory(RentPayVO rentPayVO);
	
	// 월세납부내역 상태변경
	public boolean modiStateByRentPayNo(String rentPayNo, int newState);
	
	// 계약등록시 월세납부내역 추가
	public boolean addRentPayAfterConstract(ConstractVO constractVO);
	
	// 스케줄러 7일 후 납부일인 계약들의 월세고지서 발급
	public void generateBillsScheduler();
}
