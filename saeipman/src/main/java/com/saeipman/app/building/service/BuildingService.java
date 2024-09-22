package com.saeipman.app.building.service;

import java.util.List;
import java.util.Map;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.room.service.RoomVO;




public interface BuildingService {
	public List<BuildingVO> buildingList(BuildingPageDTO pageDTO, String id);
	public BuildingVO buildingInfo(BuildingVO buildingVO);
	public int buildingInsert(BuildingVO buildingVO);
	public Map<String, Object> buildingUpdate(BuildingVO buildingVO);
	public int buildingDelete(String buildingId);
	
	//페이징
	public int totalPage(String id);
	
	//방 추가
	public Map<String, Object> roomSelectInsert(List<RoomVO> list);
	//방삭제
	public int roomDelete(String buildingId);
	

	//파일관련
	public int fileDelete(List<String> fileNames);
	public int fileNamesByGroupId(String groupId);

	// 임대인 아이디 -> 건물 리스트 조회
	public List<BuildingVO> imdaeinBuildingList(PagingDTO paging, String imdaeinId);
	
	//방 수정
	public List<RoomVO> roomSelect(RoomVO roomVO);
	public int roomUpdate(RoomVO roomVO);
	public int roomInfoDelete(RoomVO roomVO);

}
