package com.saeipman.app.admin.Service;

import java.util.Date;

import lombok.Data;

@Data
public class Member {
	private String loginId;
	private String imchainName;
	private int roomNo;
	private String imdaeinName;
	private String imdaeinEmail;
	private Date birth;
	private String phone;
	private int auth;
}
