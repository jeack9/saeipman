package com.saeipman.app.building.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.saeipman.app.building.mapper.BuildingMapper;
import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.room.service.RoomVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService{
	private final BuildingMapper buildingMapper;
	

	@Override
	public List<BuildingVO> buildingList(BuildingPageDTO pageDTO, String id) {
		
		return buildingMapper.selectBuildingList(pageDTO, id);
	}
	@Override
	public BuildingVO buildingInfo(BuildingVO buildingVO) {
		return buildingMapper.selectBuildingListInfo(buildingVO);
	}
	@Override
	public int buildingInsert(BuildingVO buildingVO) {
		int result = buildingMapper.selectBuildingInsert(buildingVO);
		return result;
	}
	@Override
	public Map<String, Object> buildingUpdate(BuildingVO buildingVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = buildingMapper.selectBuildingUpdate(buildingVO);
		
		if(result == 1) {
			isSuccessed = true;
			//map.put("list", buildingMapper.buildingList());
		}
		
		map.put("success", isSuccessed);
		map.put("target", buildingVO);
		
		return map;
	}
	
	@Override
	public int buildingDelete(String buildingId) {
		//관리비 유무 체크
		//방삭제
		return buildingMapper.selectBuildingDelete(buildingId);
	}
	@Override
	public int totalPage(String id) {
		return buildingMapper.getTotalPageCount(id);
	}
	
	@Override
	public Map<String, Object> roomSelectInsert(List<RoomVO> list) {
		System.out.println();
	
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(RoomVO vo : list) {
			
			System.out.println("sdfsdf" + vo);
			buildingMapper.selectRoomInsert(vo);
			map.put("list", vo);
		}

		return map;
	}
	
}
