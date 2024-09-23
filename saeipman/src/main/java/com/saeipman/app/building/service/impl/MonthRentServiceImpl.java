package com.saeipman.app.building.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.building.mapper.MRentHistoryMapper;
import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.building.service.MonthRentService;
import com.saeipman.app.room.service.RentPayVO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthRentServiceImpl implements MonthRentService{
	private final MRentHistoryMapper mRentHistoryMapper;
	@Override
	public List<RentPayVO> mRentList(BuildingPageDTO pageDTO, String imdaeinId) {
		
		return mRentHistoryMapper.selectMRentList(pageDTO, imdaeinId);
	}
	@Override
	public int totalPage(String imdaeinId) {
		return mRentHistoryMapper.getTotalPageCount(imdaeinId);
	}
	@Override
	public List<RentPayVO> buildingNameList(String imdaeinId) {
		return mRentHistoryMapper.selectBuildingNameInfo(imdaeinId);
	}
}
