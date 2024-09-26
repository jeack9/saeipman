package com.saeipman.app.admin.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.admin.Service.QnaCmtVO;
import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.admin.mapper.QnaMapper;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.minwon.service.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaSerivceImpl implements QnaService{
	private final QnaMapper qnaMapper;

	@Override
	public List<QnaVO> qnaList(String writerId) {
		return qnaMapper.selectQnaList(writerId);
	}

	@Override
	public QnaVO qnaInfo(int postNo, PagingDTO paging) {
		QnaVO qna = qnaMapper.selectQnaInfo(postNo, paging);
		List<QnaCmtVO> parentCmts = qnaMapper.selectParentQnaCmtList(postNo, paging);
		qna.setQnaCmts(parentCmts);
		return qna;
	}

	@Override
	public int pageTotal(Criteria cri) {
		return 0;
	}

	// qna 단건등록
	@Override
	public boolean addQna(QnaVO qnaVO) {
		return qnaMapper.insertQna(qnaVO) == 1;
	}

	@Override
	public boolean addParentCmt(QnaCmtVO cmt) {
		return qnaMapper.insertParentCmt(cmt) == 1;
	}

	// 부모댓글 토탈
	@Override
	public int totalParentCmts(int postNo) {
		return qnaMapper.totalParentCmt(postNo);
	}

}
