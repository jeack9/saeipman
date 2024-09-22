package com.saeipman.app.minwon.service;


import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String type;
	private String keyword;
	
	private int auth;
	private String loginId;
	private String imdaeinId; //임대인 아이디
	private String imchainId;
	private String buildingId;
	private String roomId;
	private int acceptState;
	
	public Criteria() {//mapper에서 조회할떄 (페이지번호,amount,검색 키워드,
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {}: type.split("");
	}
}
