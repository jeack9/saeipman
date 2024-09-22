package com.saeipman.app.admin.Service;

import java.util.Date;

import lombok.Data;

@Data
public class QnaCmtVO {
	private int cmtNo;
	private int postNo;
	private String content;
	private String writerId;
	private String auth;
	private String cmtReg;
	private String cmtGroup;
	private String cmtOrder;
	private Date regDate;
}
