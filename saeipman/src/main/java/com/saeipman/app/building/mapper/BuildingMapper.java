package com.saeipman.app.building.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.room.service.RoomVO;
import com.saeipman.app.building.service.BuildingPageDTO;

public interface BuildingMapper {
	public List<BuildingVO> buildingList(@Param("pageDTO") BuildingPageDTO pageDTO, @Param("imdaeinId") String id);
	public BuildingVO buildingListInfo(BuildingVO buildingVO);
	public int buildingInsert(BuildingVO buildingVO);
	public int buildingUpdate(BuildingVO buildingVo);
	public int selectBuildingDelete(String buildingId);
	
	//페이징
	public int getTotalPageCount(String id);
	
	//자동 방생성
	public int selectRoomInsert(RoomVO roomVO);
}
