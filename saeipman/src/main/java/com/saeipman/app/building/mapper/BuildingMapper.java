package com.saeipman.app.building.mapper;

import java.util.List;
import java.util.Map;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.building.service.BuildingPageDTO;

public interface BuildingMapper {
	public List<BuildingVO> buildingList(BuildingPageDTO pageDTO);
	public BuildingVO buildingListInfo(BuildingVO buildingVO);
	public int buildingInsert(BuildingVO buildingVO);
	public int buildingUpdate(BuildingVO buildingVo);
	public int selectBuildingDelete(String buildingId);
	
	//페이징
	public int getTotalPageCount(BuildingPageDTO pageDTO);
}
