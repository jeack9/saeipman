package com.saeipman.app.file.service;

import java.util.List;

public interface FileService {
	public String fileGroupId(FileVO fileVO);
	public int fileInsert(FileVO fIleVO);
	
	//file_name
	public List<String> getFileName(String buildingId);
}
