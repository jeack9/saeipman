package com.saeipman.app.file.mapper;


import java.util.List;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.file.service.FileVO;

public interface FileMapper {
	public String selectGroupId(FileVO fileVO);
	public int selectFileInsert(FileVO fileVO);
	
	//file_name
	public List<String> selectFileName(String buildingId);
	
	//업데이트 시 그룹아이디
	public int selectFileUpdateGroupId(BuildingVO buildingVO);
}
