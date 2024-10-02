package com.saeipman.app.payment.service;

import java.sql.Date;

import lombok.Data;

@Data
public class PaymentVO {

	private String paymentMonth;// 납부일자 - 관리비
	private int gaguGwanlibi;
	private String monthGwanlibiNo; //월별 관리비 번호
	private String buildingId;
	private String paymentHistory;
	private String gaguPaymentHistoryNo; //가구 납부 내역 번호
	private int paymentYN; //관리비 납부 여부
	
	private String realPaymentDate; //실납부일자 - 월세
	private String paymentDate; //계약서상 납부일자 - 월세
	private String mRentHistoryNo; //납부 내역 번호(월세)
	private int paymentState; //월세 납부 여부
	private int realPaymentMoney; //월세 납부액
	private int paymentMoney; //월세 납부액
	
	private int constractState; //계약현황
	
	private String imdaeinId; // 임대인 아이디
	private String imchainPhone; //임차인 아이디(연락처)
	private Date expDate;//만료일
	

	public String getmRentHistoryNo() {
		return mRentHistoryNo;
	}

	public void setmRentHistoryNo(String mRentHistoryNo) {
		this.mRentHistoryNo = mRentHistoryNo;
	}
	
	
	

}
