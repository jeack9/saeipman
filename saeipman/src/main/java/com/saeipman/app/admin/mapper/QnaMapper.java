package com.saeipman.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.admin.Service.QnaCmtVO;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.file.service.FileVO;

public interface QnaMapper {
	List<QnaVO> selectQnaList(String writerId);
	
	QnaVO selectQnaInfo(@Param("postNo") int postNO, @Param("paging") PagingDTO paging);
	
	// 부모댓글 토탈
	public int totalParentCmt(int postNo);
	
	// qna 단건등록
	public int insertQna(QnaVO qnaVO);
	
	// 부모댓글 목록조회
	public List<QnaCmtVO> selectParentQnaCmtList(@Param("postNo") int postNO, @Param("paging") PagingDTO paging);
	
	// 자식댓글 목록조회
	public List<QnaCmtVO> selectChildQnaCmtList(int cmtGroup);
	
	// 부모댓글 단건등록
	public int insertParentCmt(QnaCmtVO cmt);
	
	// 파일 목록조회
	public List<FileVO> selectFiles(int groupId);
	
}
