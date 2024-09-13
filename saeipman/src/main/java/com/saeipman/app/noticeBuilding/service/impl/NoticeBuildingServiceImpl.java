package com.saeipman.app.noticeBuilding.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.noticeBuilding.mapper.NoticeBuildingMapper;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingService;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;
import com.saeipman.app.noticeBuilding.utils.PagingSearchDTO;

@Service
public class NoticeBuildingServiceImpl implements NoticeBuildingService {

	private NoticeBuildingMapper noticeBuildingMapper;
	
	@Autowired
	public NoticeBuildingServiceImpl(NoticeBuildingMapper noticeBuildingMapper) {
		this.noticeBuildingMapper = noticeBuildingMapper;
	}

	@Override //전체조회
	public List<NoticeBuildingVO> noticeBuildingList(PagingSearchDTO pgsc) {
		return noticeBuildingMapper.selectNoticeBuildingAll(pgsc);
	}

	@Override //단건조회
	public NoticeBuildingVO noticeBuildingSelect(NoticeBuildingVO noticeBuildingVO) {
		return noticeBuildingMapper.selectNoticeBuildingInfo(noticeBuildingVO);
	}

	@Override //등록
	public int noticeBuildingInsert(NoticeBuildingVO noticeBuildingVO) {
		int result = noticeBuildingMapper.insertNoticeBuilding(noticeBuildingVO);
		return result == 1? noticeBuildingVO.getPostNo() : -1;
	}

	@Override //수정
	public Map<String, Object> noticeBuildingUpdate(NoticeBuildingVO noticeBuildingVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = noticeBuildingMapper.updateNoticeBuilding(noticeBuildingVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(today);
		
		map.put("date", updateDate);
		map.put("result", isSuccessed);
		map.put("target", noticeBuildingVO);
		
		return map;
	}

	@Override //삭제
	public int noticeBuildingDelete(int postNO) {
		return noticeBuildingMapper.deleteNoticeBuilding(postNO);
	}

	@Override //조회수 증가
	public int noticeBuildingViews(NoticeBuildingVO noticeBuildingVO) {
		return noticeBuildingMapper.updateViewNoticeBuilding(noticeBuildingVO);
	}

	@Override //게시글 전체 건수 조회
	public int totalPage(PagingSearchDTO pgsc) {
		return noticeBuildingMapper.totalBoardNoticeBuilding(pgsc);
	}

	@Override //임대인 건물 목록 출력
	public List<BuildingVO> imdaeinBuilding(BuildingVO buildingVO) {
		return noticeBuildingMapper.imdaeinBuildingInfo(buildingVO);
	}

	@Override //파일 업로드
	public List<String> getFileInfo(int postNo) {
		return noticeBuildingMapper.getFile(postNo);
	}

}
