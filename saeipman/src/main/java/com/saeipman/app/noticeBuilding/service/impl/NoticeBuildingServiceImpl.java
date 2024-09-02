package com.saeipman.app.noticeBuilding.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.noticeBuilding.mapper.NoticeBuildingMapper;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingService;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;

@Service
public class NoticeBuildingServiceImpl implements NoticeBuildingService {

	private NoticeBuildingMapper noticeBuildingMapper;
	
	@Autowired
	public NoticeBuildingServiceImpl(NoticeBuildingMapper noticeBuildingMapper) {
		this.noticeBuildingMapper = noticeBuildingMapper;
	}

	@Override
	public List<NoticeBuildingVO> noticeBuildingList() {
		return noticeBuildingMapper.selectNoticeBuildingAll();
	}

	@Override
	public NoticeBuildingVO noticeBuildingSelect(NoticeBuildingVO noticeBuildingVO) {
		return noticeBuildingMapper.selectNoticeBuildingInfo(noticeBuildingVO);
	}

	@Override
	public int noticeBuildingInsert(NoticeBuildingVO noticeBuildingVO) {
		int result = noticeBuildingMapper.insertNoticeBuilding(noticeBuildingVO);
		return result == 1? noticeBuildingVO.getPostNo() : -1;
	}

	@Override
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

	@Override
	public int noticeBuildingDelete(int postNO) {
		return noticeBuildingMapper.deleteNoticeBuilding(postNO);
	}

}
