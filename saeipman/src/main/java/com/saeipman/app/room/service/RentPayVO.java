package com.saeipman.app.room.service;

import java.util.Date;

import lombok.Data;
// 월세 납부내역
@Data
public class RentPayVO {
	private String mRentHistoryNo; // 얼세납부내역번호
	private String constractNo; // 계약번호
	private String roomId; // 방아이디
	private Date paymentDate; // 계약상 납부일
	private Date realPaymentDate; // 실제 납부일
	private int paymentMoney; // 계약상 월세
	private int realPaymentMoney; // 실제 납부 월세
	private String depositorName; // 임차인이름
	private int paymentState; // 납부상태 -1 연체, 0 대기, 1 납부
	private int alert_type; // 0 연체통지
	private String memo; // 메모
	public String getmRentHistoryNo() {
		return mRentHistoryNo;
	}
	public void setmRentHistoryNo(String mRentHistoryNo) {
		this.mRentHistoryNo = mRentHistoryNo;
	}
	
}
