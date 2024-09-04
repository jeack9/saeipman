package com.saeipman.app.member.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ImdaeinVO {
	private String imdaeinId;
	private String imdaeinName;
	private String imdaeinEmail;
	private Date birth;
	private String phone;
}
