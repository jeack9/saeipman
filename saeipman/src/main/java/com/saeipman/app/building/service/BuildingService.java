package com.saeipman.app.building.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.room.service.RoomVO;




public interface BuildingService {
	public List<BuildingVO> buildingDetail(BuildingPageDTO pageDTO);
	public BuildingVO buildingInfo(BuildingVO buildingVO);
	public int insertBuilding(BuildingVO buildingVO);
	public Map<String, Object> updateBuilding(BuildingVO buildingVO);
	public int buildingDelete(String buildingId);
	
	//페이징
	public int totalPage(BuildingPageDTO pageDTO);
	
	//방 추가
	public int roomSelectInsert(List<RoomVO> list);
}
