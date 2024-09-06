package com.saeipman.app.minwon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.minwon.mapper.ReplyMapper;
import com.saeipman.app.minwon.service.ReplyService;
import com.saeipman.app.minwon.service.ReplyVO;

import groovy.util.logging.Log4j;
import lombok.AllArgsConstructor;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	
	private ReplyMapper replyMapper;

	@Override
	public int cmtInsert(ReplyVO vo) {
		return replyMapper.insertCmt(vo);
	}

	@Override
	public ReplyVO cmtList(Long minwonCmtNo) {
		return replyMapper.listCmt(minwonCmtNo);
	}

	@Override
	public int cmtDelete(Long minwonCmtNo) {
		return cmtDelete(minwonCmtNo);
	}

	@Override
	public List<ReplyVO> withPagingList(Long postNo) {
		return replyMapper.listWithPaging(postNo);
	}

	
	
}
