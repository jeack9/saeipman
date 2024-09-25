package com.saeipman.app.admin.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saeipman.app.admin.Service.AdminService;
import com.saeipman.app.admin.Service.Member;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.admin.mapper.AdminMapper;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.member.service.ImdaeinVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final AdminMapper adminMapper;
	
	@Override
	public List<Member> imdaeinList() {
		return adminMapper.selectImdaeinList();
	}

	@Override
	public List<Member> imchainList() {
		return adminMapper.selectImchainList();
	}
	
	// 공지사항 목록 조회 (페이징 및 검색)
	@Override
    public List<NoticeVO> noticeList(PagingDTO paging, String keyword) {
        return adminMapper.selectNoticeList(paging, keyword);
    }

    // 공지사항 총 개수 조회 (검색어에 따라)
	@Override
    public int countNotices(String keyword) {
        return adminMapper.totalNotice(keyword);
    }

	
	// 공지사항 단건조회
	@Override
	@Transactional
	public NoticeVO noticeInfo(int postNo) {
		NoticeVO notice = adminMapper.selectNoticeInfo(postNo);
		adminMapper.updateNoticeViews(postNo);
		return notice;
	}

	@Override
	public void removeNotice(int postNo) {
		adminMapper.deleteNotice(postNo);
	}

	// 단건등록 -- postNo 반환
	@Override
	public Map<String, Object> addNotice(NoticeVO noticeVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = adminMapper.insertNotice(noticeVO);
		if(result == 1) {
			map.put("retCode", true);
			map.put("postNo", noticeVO.getPostNo());
			return map;
		}
		map.put("retCode", false);
		return map;
	}

	// 단건수정 -- postNo 반환
	@Override
	public Map<String, Object> modiNotice(NoticeVO noticeVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = adminMapper.updateNotice(noticeVO);
		if(result == 1) {
			map.put("retCode", true);
			map.put("postNo", noticeVO.getPostNo());
			return map;
		}
		map.put("retCode", false);
		return map;
	}

}
