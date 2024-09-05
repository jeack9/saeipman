package com.saeipman.app.file.mapper;


import java.util.List;

import com.saeipman.app.file.service.FileVO;

public interface FileMapper {
	public String selectGroupId(FileVO fileVO);
	public int selectFileInsert(FileVO fileVO);
	
	//file_name
	public List<String> selectFileName(String buildingId);
}
