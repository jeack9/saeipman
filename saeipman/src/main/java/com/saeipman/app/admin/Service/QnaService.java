package com.saeipman.app.admin.Service;

import java.util.List;

import com.saeipman.app.minwon.service.Criteria;

public interface QnaService {
	//QnA 목록
	List<QnaVO> qnaList();
	//QnA 조회
	QnaVO qnaInfo(QnaVO qnaVO);
	
	//페이지
	public int pageTotal(Criteria cri);
}
