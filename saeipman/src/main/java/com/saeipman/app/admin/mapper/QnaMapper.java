package com.saeipman.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.admin.Service.QnaCmtVO;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.support.service.SearchQna;

public interface QnaMapper {
	// qna 목록 조회 페이징
	List<QnaVO> selectQnaList(SearchQna search);
	
	// qna 토탈
	public int totalQna(SearchQna search);
	
	QnaVO selectQnaInfo(@Param("postNo") int postNO, @Param("paging") PagingDTO paging);
	
	// 부모댓글 토탈
	public int totalParentCmt(int postNo);
	
	// qna 단건등록
	public int insertQna(QnaVO qnaVO);
	
	// qna 답변상태 변경
	public int updateStateToOne(int postNo);
	
	// 부모댓글 목록조회
	public List<QnaCmtVO> selectParentQnaCmtList(@Param("postNo") int postNO, @Param("paging") PagingDTO paging);
	
	// 부모댓글 단건등록
	public int insertParentCmt(QnaCmtVO cmt);
	
	// 자식댓글 목록조회
	public List<QnaCmtVO> selectChildQnaCmtList(int cmtGroup);
	
	// 자식댓글 단건등록
	public int insertChildCmt(QnaCmtVO cmt);
	
	// 파일 목록조회
	public List<FileVO> selectFiles(int groupId);
	
	// 댓글 단건삭제(실제 삭제 x, 정보 수정)
	public int deleteCmt(QnaCmtVO cmt);
	
	// 댓글 단건조회
	public QnaCmtVO selectCmtInfo(int cmtNo);
}
