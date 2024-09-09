package com.saeipman.app.gwanlibi.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GwanlibiVO {
	
	private String monthGwanlibiNo;       // 월 관리비 번호 - PK
	private String buildingId;			  // 건물 식별 ID - FK
	private int totalMoney; 			  // 관리비 총 금액
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date paymentMonth;  		  // 관리비 납부 월	
	private String buildingName; 		  // 건물 이름
	private int previousMonth;			  // 전월 관리 비용
	private int thisMonth;				  // 금월 관리 비용
	private String gwanlibiName;		  // 관리비 이름
	
	private int gaguGwanlibi; 			  // 가구별 관리비
	private double gwanlibiByGagu;        // 가구별 관리비
	private String strGwanlibiByGagu;	  // 가구별 관리비 (,)
	
	private int gwanlibiItemMoney;		  // 항목별 관리비
	private String strGwanlibiItemMoney;  // 항목별 관리비 (,)
}
