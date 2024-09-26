package com.saeipman.app.admin.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.admin.mapper.QnaMapper;
import com.saeipman.app.minwon.service.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QanSerivceImpl implements QnaService{
	private final QnaMapper qnaMapper;

	@Override
	public List<QnaVO> qnaList() {
		return qnaMapper.selectQnaList();
	}

	@Override
	public QnaVO qnaInfo(QnaVO qnaVO) {
		return qnaMapper.selectQnaInfo(qnaVO);
	}

	@Override
	public int pageTotal(Criteria cri) {
		return 0;
	}

}
