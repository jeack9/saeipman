package com.saeipman.app.admin.Service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.saeipman.app.admin.Service.QnaCmtVO;
import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.admin.mapper.QnaMapper;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.support.service.SearchQna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaSerivceImpl implements QnaService {
	private final QnaMapper qnaMapper;

	// 댓글 작성시간 계산
	private String getTimeAgo(Date date) {
		LocalDateTime createdDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(createdDate, now);

		long seconds = duration.getSeconds();
		if (seconds < 60) {
			//return seconds + "초 전";
			return "방금 전";
		}

		long minutes = seconds / 60;
		if (minutes < 60) {
			return minutes + "분 전";
		}

		long hours = minutes / 60;
		if (hours < 24) {
			return hours + "시간 전";
		}

		long days = hours / 24;
		if (days < 30) {
			return days + "일 전";
		}

		long months = days / 30;
		if (months < 12) {
			return months + "달 전";
		}

		long years = months / 12;
		return years + "년 전";
	}

	@Override
	public List<QnaVO> qnaList(SearchQna search) {
		return qnaMapper.selectQnaList(search);
	}

	@Override
	public QnaVO qnaInfo(int postNo, PagingDTO paging) {
		QnaVO qna = qnaMapper.selectQnaInfo(postNo, paging);
		List<QnaCmtVO> parentCmts = qnaMapper.selectParentQnaCmtList(postNo, paging);
		qna.setQnaCmts(parentCmts);
		// 댓글작성 시간 계산
		for (QnaCmtVO parentCmt : qna.getQnaCmts()) {
			// 대댓글 시간계산
			for (QnaCmtVO childCmt : parentCmt.getChildCmts()) {
				String timeAgo = getTimeAgo(childCmt.getRegDate());
				childCmt.setTimeAgo(timeAgo);
			}
			String timeAgo = getTimeAgo(parentCmt.getRegDate());
			parentCmt.setTimeAgo(timeAgo);
		}
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

	// 자식댓글 단건등록
	@Override
	public QnaCmtVO addChildCmt(QnaCmtVO cmt) {
		qnaMapper.insertChildCmt(cmt);
		return cmtInfo(cmt.getCmtNo());
	}

	// qna 답변완료 상태변경
	@Override
	public void changeStateToOne(int postNo) {
		qnaMapper.updateStateToOne(postNo);
	}

	// qna 댓글 단건삭제
	@Override
	public QnaCmtVO removeCmt(QnaCmtVO cmt) {
		int result = qnaMapper.deleteCmt(cmt);
		return qnaMapper.selectCmtInfo(cmt.getCmtNo());
	}

	@Override
	public QnaCmtVO cmtInfo(int cmtNo) {
		QnaCmtVO cmt = qnaMapper.selectCmtInfo(cmtNo);
		String timeAgo = getTimeAgo(cmt.getRegDate());
		cmt.setTimeAgo(timeAgo);
		return cmt;
	}

	@Override
	public int totalQna(SearchQna search) {
		return qnaMapper.totalQna(search);
	}

}
