package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class GwanlibiPaymentVO {
	
	private int roomNo;
	private int paymentYn;
	private int ipjuState;
	private String strPaymentState;
	
	private String buildingId;
	
}
