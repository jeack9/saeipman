package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class GwanlibiDetailsVO {
	// 관리비 상세 내역
	private String buildingId;			// 관리비 상세 번호 - PK
	private String monthGwanlibiNo;		// 월별 관리비 번호 - FK
	private Integer gwanlibiItemNo;		// 관리비 항목 번호 - FK
	private Integer gwanlibiItemMoney;	// 관리비 항목 금액
}
