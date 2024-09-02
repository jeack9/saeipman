package com.saeipman.app.gwanlibi.mapper;

import java.util.List;

import com.saeipman.app.gwanlibi.service.MonthGwanlibiVO;

public interface MonthGwanlibiMapper {
	public List<MonthGwanlibiVO> selectAllMonGwanlibiList(String imdaeinId);
}
