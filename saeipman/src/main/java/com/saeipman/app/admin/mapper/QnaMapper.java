package com.saeipman.app.admin.mapper;

import java.util.List;

import com.saeipman.app.admin.Service.QnaVO;

public interface QnaMapper {
	List<QnaVO> selectQnaList();
	QnaVO selectQnaInfo(QnaVO qnaVO);
	
}
