package com.saeipman.app.minwon.service;

import java.util.List;

public interface ReplyService {
    // 새로운 댓글 등록
    void insertCmt(ReplyVO vo);

    // 대댓글 등록
    void insertReplyCmt(ReplyVO vo);

    // 댓글 및 대댓글 리스트 조회
    List<ReplyVO> listCmt(int postNo);

    // 댓글 삭제
    void deleteCmt(int minwonCmtNo);
    
	List<ReplyVO> getReplyList(int parentCmtNo);
	
}
