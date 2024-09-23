package com.saeipman.app.gwanlibi.service;

import lombok.Data;

@Data
public class GwanlibiMsgVO {
	
	private String buildingId;		// 건물 번호
	private String buildingName;
	private int ipjuState;			// 입주 현황
	
	private String imchainPhone;	// 임차인 연락처
	
	private String imdaeinId;		// 임대인 아이디
	private String phone;			// 임대인 연락처
	private int paymentYn;
}