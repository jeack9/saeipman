package com.saeipman.app.admin.Service;

import java.util.List;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.minwon.service.Criteria;

public interface QnaService {
	//QnA 목록
	List<QnaVO> qnaList(String writerId);
	//QnA 조회
	QnaVO qnaInfo(int postNo, PagingDTO paging);
	
	//페이지
	public int pageTotal(Criteria cri);
	
	// qna 단건등록
	public boolean addQna(QnaVO qnaVO);
	
	// 댓글(부모) 단건등록
	public boolean addParentCmt(QnaCmtVO cmt);
	
	// 부모댓글 토탈
	public int totalParentCmts(int postNo);
}
