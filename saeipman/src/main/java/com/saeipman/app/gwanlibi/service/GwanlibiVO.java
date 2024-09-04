package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class GwanlibiVO {
	
	private String monthGwanlibiNo;       // 월 관리비 번호 - PK
	private String buildingId;			  // 건물 식별 ID - FK
	private int totalMoney; 			  // 관리비 총 금액
	private String paymentMonth;		  // 납부 월
	private int gaguGwanlibi; 			  // 가구별 관리비
	private String buildingName;
	private int previousMonth;
	private int thisMonth;
	
	private String gwanlibiName;
	private Integer gwanlibiItemMoney;

}
