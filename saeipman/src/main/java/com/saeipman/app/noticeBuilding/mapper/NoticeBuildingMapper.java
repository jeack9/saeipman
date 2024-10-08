package com.saeipman.app.noticeBuilding.mapper;

import java.util.List;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;
import com.saeipman.app.noticeBuilding.utils.PagingSearchDTO;

public interface NoticeBuildingMapper {
	
		//전체조회
		public List<NoticeBuildingVO> selectNoticeBuildingAll(PagingSearchDTO pgsc);
		
		//단건조회
		public NoticeBuildingVO selectNoticeBuildingInfo(NoticeBuildingVO noticeBuildingVO);
		
		//등록(postNo, title, writer, content, chumbuimage, 
		public int insertNoticeBuilding(NoticeBuildingVO noticeBuildingVO);
		
		//수정(title, content, category, chumbuImage, visitsDate)
		public int updateNoticeBuilding(NoticeBuildingVO noticeBuildingVO);
		
		//삭제
		public int deleteNoticeBuilding(int postNO);
		
		//조회수
		public int updateViewNoticeBuilding(NoticeBuildingVO noticeBuildingVO);
		
		//페이징 - 글 총 개수
		public int totalBoardNoticeBuilding(PagingSearchDTO pgsc);
		
		//임대인 건물 목록
		public List<BuildingVO> imdaeinBuildingInfo(BuildingVO buildingVO);
		
		//파일
		public List<String> selectFileName(int postNo);
		
		
		
}
