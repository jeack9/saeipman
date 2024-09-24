package com.saeipman.app.building.service;

import java.util.List;

import com.saeipman.app.room.service.RentPayVO;

public interface MonthRentService {
	public List<RentPayVO> mRentList(BuildingPageDTO pageDTO, String imdaeinId); 
	//리스트 총 수
	public int totalPage(String imdaeinId);
	//건물 명
	public List<RentPayVO>  buildingNameList(String imdaeinId);
}
