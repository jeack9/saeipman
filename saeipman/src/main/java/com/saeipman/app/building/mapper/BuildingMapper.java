package com.saeipman.app.building.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.room.service.RoomVO;
import com.saeipman.app.building.service.BuildingPageDTO;

public interface BuildingMapper {
	public List<BuildingVO> selectBuildingList(@Param("pageDTO") BuildingPageDTO pageDTO, @Param("imdaeinId") String id);
	public BuildingVO selectBuildingListInfo(BuildingVO buildingVO);
	public int selectBuildingInsert(BuildingVO buildingVO);
	public int selectBuildingUpdate(BuildingVO buildingVo);
	public int selectBuildingDelete(String buildingId);
	
	//페이징
	public int getTotalPageCount(String id);
	
	//자동 방생성
	public int selectRoomInsert(RoomVO roomVO);
	
	//방삭제
	public int selectRoomDelete(String buildingId);
	

	//파일
	public int selectDeleteFileName(String fileName);
	public int selectFileNamesByGroupId(String groupId);

	// 임대인아이디 -> 건물 리스트 조회 
	public List<BuildingVO> selectImdaeinBuildingList(@Param("paging") PagingDTO paging, @Param("imdaeinId") String imdaeinId);

}
