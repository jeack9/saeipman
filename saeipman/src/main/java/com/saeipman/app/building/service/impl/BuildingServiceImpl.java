package com.saeipman.app.building.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.saeipman.app.building.mapper.BuildingMapper;
import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
@Service
public class BuildingServiceImpl implements BuildingService{
	private BuildingMapper buildingMapper;
	
	public BuildingServiceImpl(BuildingMapper buildingMapper) {
		this.buildingMapper = buildingMapper;
	}
	@Override
	public List<BuildingVO> buildingDetail() {
		
		return buildingMapper.buildingList();
	}
	@Override
	public BuildingVO buildingInfo(BuildingVO buildingVO) {
		return buildingMapper.buildingListInfo(buildingVO);
	}
	@Override
	public int insertBuilding(BuildingVO buildingVO) {
		int result = buildingMapper.buildingInsert(buildingVO);
		return result==1?1:-1;
	}
	@Override
	public Map<String, Object> updateBuilding(BuildingVO buildingVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		int result = buildingMapper.buildingUpdate(buildingVO);
		
		if(result == 1) {
			isSuccessed = true;
			map.put("list", buildingMapper.buildingList());
		}
		
		map.put("success", isSuccessed);
		map.put("target", buildingVO);
		
		return map;
	}
	@Override
	public int buildingDelete(String buildingId) {
		return buildingMapper.selectBuildingDelete(buildingId);
	}
}
