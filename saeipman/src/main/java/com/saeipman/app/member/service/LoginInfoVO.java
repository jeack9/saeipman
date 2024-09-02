package com.saeipman.app.member.service;

import lombok.Data;

@Data
public class LoginInfoVO {
	private String loginId;
	private String pw;
	private int auth;
}
