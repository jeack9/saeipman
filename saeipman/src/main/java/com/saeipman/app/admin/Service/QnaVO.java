package com.saeipman.app.admin.Service;

import java.util.Date;
import java.util.List;

import com.saeipman.app.file.service.FileVO;

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
	private int answerState;
	private String writerId;
	List<FileVO> qnaFiles;
	List<QnaCmtVO> qnaCmts; 
}
