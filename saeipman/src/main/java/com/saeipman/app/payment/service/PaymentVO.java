package com.saeipman.app.payment.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PaymentVO {

	private String payMonth;
	private int totalMoney;
	private int gaguGwanlibi;
	private String GwanlibiNo;
	private String buildingId;
	//private String roomId;
	private String paymentHistory;
	private int paymentYN;
	
	

}
