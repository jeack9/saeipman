package com.saeipman.app.admin.mapper;

import java.util.List;

import com.saeipman.app.admin.Service.Member;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.member.service.ImdaeinVO;

public interface AdminMapper {
	List<Member> selectImdaeinList();
	List<Member> selectImchainList();
	List<Member> selectMemberList();
	
	// 공지리스트
	List<NoticeVO> selectNoticeList(PagingDTO paging, String keyword);
	// 검색키워드에 따른 공지 개수
	int totalNotice(String keyword);
	// 공지 단건조회
	NoticeVO selectNoticeInfo(int postNo);
	// 공지 조회수 업데이트
	int updateNoticeViews(int postNo);
	// 공지 단건삭제
	int deleteNotice(int postNo);
}
