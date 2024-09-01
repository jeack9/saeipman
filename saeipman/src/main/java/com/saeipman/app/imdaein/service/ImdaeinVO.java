package com.saeipman.app.imdaein.service;

import java.util.Date;

import lombok.Data;

@Data
public class ImdaeinVO {
	private String imdaeinId; // 임대인 아이디
	private String imdaeinName; // 임대인 이름
	private String imdaeinEmail; // 임대인 이메일
	private Date birth; // 생년월일
	private String phone; // 휴대폰 번호
}
