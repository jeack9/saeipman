package com.saeipman.app.payment.service;

import lombok.Data;

@Data
public class PaymentVO {

	private String paymentMonth;
	private int totalMoney;
	private int gaguGwanlibi;
	private String monthGwanlibiNo;
	private String buildingId;
	private String paymentHistory;
	private int paymentYN;
	private String imchainPhone;
	
	

}
