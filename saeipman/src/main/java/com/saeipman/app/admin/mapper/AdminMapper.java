package com.saeipman.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.admin.Service.Member;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.member.service.ImdaeinVO;

public interface AdminMapper {
	List<Member> selectImdaeinList();
	List<Member> selectImchainList();
	List<Member> selectMemberList();
	
	// 파일목록 조회
	List<FileVO> selectFiles(String groupId);
	
	// 공지리스트
	List<NoticeVO> selectNoticeList(@Param("paging") PagingDTO paging, @Param("keyword") String keyword);
	// 검색키워드에 따른 공지 개수
	int totalNotice(String keyword);
	// 공지 단건조회
	NoticeVO selectNoticeInfo(int postNo);
	// 공지 조회수 업데이트
	int updateNoticeViews(int postNo);
	// 공지 단건삭제
	int deleteNotice(int postNo);
	// 공디 단건수정
	int updateNotice(NoticeVO noticeVO);
	// 공지 단건등록
	int insertNotice(NoticeVO noticeVO);
}
