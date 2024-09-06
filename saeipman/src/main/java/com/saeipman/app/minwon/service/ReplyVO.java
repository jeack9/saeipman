package com.saeipman.app.minwon.service;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private Integer postNo; //게시글 번호
	private Integer minwonCmtNo; //댓글 인덱스
	
	private String content; //작성자
	private int writerAuth; //작성자 권한
	private int cmtRep; //댓글 계층
	private int cmtGroup; //댓글 그룹
	private int cmtOrder; //댓글 순서
	private Date regDate; //작성일
}
