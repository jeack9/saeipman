package com.saeipman.app.building.service.impl;

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
	public List<BuildingVO> buildingDetail(BuildingPageDTO pageDTO) {
		
		return buildingMapper.buildingList(pageDTO);
	}
	@Override
	public BuildingVO buildingInfo(BuildingVO buildingVO) {
		return buildingMapper.buildingListInfo(buildingVO);
	}
	@Override
	public int insertBuilding(BuildingVO buildingVO) {
		int result = buildingMapper.buildingInsert(buildingVO);
		return result;
	}
	@Override
	public Map<String, Object> updateBuilding(BuildingVO buildingVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = buildingMapper.buildingUpdate(buildingVO);
		
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
		return buildingMapper.selectBuildingDelete(buildingId);
	}
	@Override
	public int totalPage(BuildingPageDTO pageDTO) {
		return buildingMapper.getTotalPageCount(pageDTO);
	}
	
	@Override
	public int roomSelectInsert(List<RoomVO> list) {
		for(RoomVO vo : list) {
			buildingMapper.selectRoomInsert(vo);
		}
		
		return 1;
	}
	
}
