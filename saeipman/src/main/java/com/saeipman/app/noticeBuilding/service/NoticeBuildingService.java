package com.saeipman.app.noticeBuilding.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.building.service.BuildingVO;import com.saeipman.app.commom.security.SecurityUtil;
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
	
	// 페이징
	public int totalPage(PagingSearchDTO pgsc);

	// 임대인 소유 건물 조회
	public List<BuildingVO> imdaeinBuilding(BuildingVO buildingVO);
	
	//첨부파일
	public List<String> getFileName(int postNo);
	public int fileDelete(List<String> fileNames);
	public int fileNamesByGroupId(String groupId);
	
	
}
