package com.saeipman.app.admin.Service;

import java.util.Date;

import lombok.Data;

@Data
public class QnaVO {
	private int postNo;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date modDate;
	private String groupId;
	private String answerState;
	private String writerId;
}
