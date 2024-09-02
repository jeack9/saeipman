package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class GwanlibiHistoryVO {
	// 가구별 관리비 내역
	private String gaguPaymentHistoryNo; // 가구별 납부 내역 번호 - PK
	private String monthGwanlibiNo;		 // 월별 관리비 번호 - FK
	private Integer paymentYn;			 // 관리비 납부 여부
	private String roomId;				 // 호실 식별 ID - FK
}
