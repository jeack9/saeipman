package com.saeipman.app.minwon.mapper;

import java.util.List;



import com.saeipman.app.minwon.service.ReplyVO;

public interface ReplyMapper {
	//댓글등록
	public int insertCmt(ReplyVO vo);
	   
	//대댓글 등록
	public int insertReplyCmt(ReplyVO vo);
	   
	//댓글 및 대댓글 리스트 조회
	List<ReplyVO> listCmt(int postNo);
	   
	//특정 댓글 삭제
	public int deleteCmt(Long minwonCmtNo);
}
