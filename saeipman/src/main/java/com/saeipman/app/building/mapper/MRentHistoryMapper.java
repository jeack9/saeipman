package com.saeipman.app.building.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.room.service.RentPayVO;

public interface MRentHistoryMapper {
	public List<RentPayVO> selectMRentList(@Param("pageDTO") BuildingPageDTO pageDTO,@Param("imdaeinId") String imdaeinId);
	
	//리스트 총수
	public int getTotalPageCount(String imdaeinId);
	
	//건물 이름가져오기
	public List<RentPayVO> selectBuildingNameInfo(String imdaeinId);
}
