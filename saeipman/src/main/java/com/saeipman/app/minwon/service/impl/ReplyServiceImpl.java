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
	public void insertCmt(ReplyVO vo) {
		replyMapper.insertCmt(vo);
		
	}

	@Override
	public void insertReplyCmt(ReplyVO vo) {
		replyMapper.insertCmt(vo);
		
	}

	@Override
	public List<ReplyVO> listCmt(int postNo) {
		return replyMapper.listCmt(postNo);
	}

	@Override
	public void deleteCmt(Long minwonCmtNo) {
		replyMapper.deleteCmt(minwonCmtNo);
		
	}

	@Override
	public List<ReplyVO> getReplyList(int parentCmtNo) {
		return replyMapper.listCmt(parentCmtNo);
	}

	
	
}
