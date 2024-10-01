package com.saeipman.app.gwanlibi.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GwanlibiPaymentVO {
	
	private int roomNo;
	private int paymentYn;
	private int ipjuState;
	private String strPaymentState;
	
	private String buildingId;
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date paymentMonth;
	
}
