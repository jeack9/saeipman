package com.saeipman.app.building.service;

import java.util.List;
import java.util.Map;

public interface BuildingService {
	public List<BuildingVO> buildingDetail();
	public BuildingVO buildingInfo(BuildingVO buildingVO);
	public int insertBuilding(BuildingVO buildingVO);
	public Map<String, Object> updateBuilding(BuildingVO buildingVO);
	public int buildingDelete(String buildingId);
}
