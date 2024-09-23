package com.saeipman.app.building.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.room.service.RentPayVO;

public interface MRentHistoryMapper {
	public List<RentPayVO> selectMRentList(@Param("pageDTO") BuildingPageDTO pageDTO,@Param("imdaeinId") String imdaeinId);
}
