package com.saeipman.app.file.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.file.mapper.FileMapper;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.file.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	private FileMapper fileMapper;
	
	public FileServiceImpl(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	
	@Override
	public String fileGroupId(FileVO fileVO) {
		return fileMapper.selectGroupId(fileVO);
	}
	@Override
	public int fileInsert(FileVO fileVO) {
		return fileMapper.selectFileInsert(fileVO);
	}
	@Override
	public List<String> getFileName(String buildingId) {
		return fileMapper.selectFileName(buildingId);
	}
	@Override
	public int getUpdateGroupId(BuildingVO buildingVO) {
		return fileMapper.selectFileUpdateGroupId(buildingVO);
	}
}
