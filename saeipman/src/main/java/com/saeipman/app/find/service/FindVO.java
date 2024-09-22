package com.saeipman.app.find.service;

import lombok.Data;

@Data
public class FindVO {
	private String imdaeinId; //임대인 아이디
	private String imdaeinName; //임대인 이름
	private String imdaeinEmail; //임대인 이메일
	private int phone; //폰번호
	private String pw;
	private String imchainId;
	private String imchainName;
	private String loginId;
	
}
