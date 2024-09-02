package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class BiuldingGwanlibiVO {
	// 건물별 관리비 항목
	private Integer gwanlibiItemNo; // 건물별 관리비 항목 번호 - PK
	private String buildingId;		// 건물 식별 ID - FK
	private String gwanlibiName;	// 건물 이름
}