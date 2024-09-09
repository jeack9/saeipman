package com.saeipman.app.noticeBuilding.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.noticeBuilding.utils.PagingSearchDTO;

public interface NoticeBuildingService {
	// 전체조회
	public List<NoticeBuildingVO> noticeBuildingList(PagingSearchDTO pgsc);

	// 단건조회
	public NoticeBuildingVO noticeBuildingSelect(NoticeBuildingVO noticeBuildingVO);

	// 등록
	public int noticeBuildingInsert(NoticeBuildingVO noticeBuildingVO);

	// 수정
	public Map<String, Object> noticeBuildingUpdate(NoticeBuildingVO noticeBuildingVO);

	// 삭제
	public int noticeBuildingDelete(int postNo);

	// 조회수 증가
	public int noticeBuildingViews(NoticeBuildingVO noticeBuildingVO);
	
	
	public int totalPage(PagingSearchDTO pgsc);

}
