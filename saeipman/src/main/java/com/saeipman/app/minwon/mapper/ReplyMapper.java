package com.saeipman.app.minwon.mapper;

import java.util.List;



import com.saeipman.app.minwon.service.ReplyVO;

public interface ReplyMapper {
	//댓글등록
	public int insertCmt(ReplyVO vo);
	
	//특정 댓글 읽기
	public ReplyVO listCmt(Long minwonCmtNo);
	
	//특정 댓글 삭제
	public int  deleteCmt(int minwonCmtNo);
	
	public List<ReplyVO> listWithPaging(Long postNo);
}
