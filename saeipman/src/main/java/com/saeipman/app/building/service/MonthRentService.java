package com.saeipman.app.building.service;

import java.util.List;

import com.saeipman.app.room.service.RentPayVO;

public interface MonthRentService {
	public List<RentPayVO> mRentList(BuildingPageDTO pageDTO, String imdaeinId); 
}
