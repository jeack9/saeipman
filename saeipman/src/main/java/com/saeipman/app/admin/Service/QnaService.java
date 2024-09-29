package com.saeipman.app.admin.Service;

import java.util.List;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.room.service.SearchVO;
import com.saeipman.app.support.service.SearchQna;

public interface QnaService {
	//QnA 목록 페이징
	List<QnaVO> qnaList(SearchQna search);
	//QnA 단건조회 -- 댓글과 함께
	QnaVO qnaInfo(int postNo, PagingDTO paging);
	// Qna 페이징 토탈
	public int totalQna(SearchQna search);
	
	//페이지
	public int pageTotal(Criteria cri);
	
	// qna 단건등록
	public boolean addQna(QnaVO qnaVO);
	
	// 댓글(부모) 단건등록
	public boolean addParentCmt(QnaCmtVO cmt);
	
	// 부모댓글 토탈
	public int totalParentCmts(int postNo);
	
	// 자식댓글 단건등록
	public QnaCmtVO addChildCmt(QnaCmtVO cmt);
	
	// qna 답변완료로 상태변경
	public void changeStateToOne(int postNo);
	
	// 댓글 단건삭제
	public QnaCmtVO removeCmt(QnaCmtVO cmt);
	
	// 댓글 단건조회
	public QnaCmtVO cmtInfo(int cmtNo);
}
