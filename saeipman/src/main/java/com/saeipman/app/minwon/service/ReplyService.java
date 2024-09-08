package com.saeipman.app.minwon.service;

import java.util.List;

public interface ReplyService {
	public int cmtInsert(ReplyVO vo);
	public ReplyVO cmtList(Long minwonCmtNo);
	public int cmtDelete(Long minwonCmtNo);
	public List<ReplyVO> withPagingList(Long postNo);
	
}
