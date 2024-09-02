package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class BasicGwanlibiVO {
	// 기본 관리비 항목
	private Integer gwanlibiItemNo;		// 기본 관리비 항목 번호 - PK
	private String gwanlibiItemName;	// 기본 관리비 항목 이름
}