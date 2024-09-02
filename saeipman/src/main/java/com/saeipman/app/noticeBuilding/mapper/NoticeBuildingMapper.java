package com.saeipman.app.noticeBuilding.mapper;

import java.util.List;

import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;

public interface NoticeBuildingMapper {
	
		//전체조회
		public List<NoticeBuildingVO> selectNoticeBuildingAll();
		
		//단건조회
		public NoticeBuildingVO selectNoticeBuildingInfo(NoticeBuildingVO noticeBuildingVO);
		
		//등록(postNo, title, writer, content, chumbuimage, 
		public int insertNoticeBuilding(NoticeBuildingVO noticeBuildingVO);
		
		//수정(title, content, category, chumbuImage, visitsDate)
		public int updateNoticeBuilding(NoticeBuildingVO noticeBuildingVO);
		
		//삭제
		public int deleteNoticeBuilding(int postNO);
}
