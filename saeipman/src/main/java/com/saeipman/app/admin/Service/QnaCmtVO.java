package com.saeipman.app.admin.Service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class QnaCmtVO {
	private int cmtNo;
	private int postNo;
	private String content;
	private String writerId;
	private int auth;
	private String cmtRep;
	private String cmtGroup;
	private String cmtOrder;
	private Date regDate;
	private int state;
	private List<QnaCmtVO> childCmts;
	private String timeAgo;
}
