package com.saeipman.app.member.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ImdaeinVO {
	private String imaeinId;
	private String imaeinName;
	private String imaeinEmail;
	private Date birth;
	private String phone;
	private String pw;
}
