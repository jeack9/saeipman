package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.mapper.MonthGwanlibiMapper;
import com.saeipman.app.gwanlibi.service.MonthGwanlibiService;
import com.saeipman.app.gwanlibi.service.MonthGwanlibiVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonthGwanlibiServiceImpl implements MonthGwanlibiService{
	
	private MonthGwanlibiMapper monthGwanlibiMapper;
	
//	public MonthGwanlibiServiceImpl(MonthGwanlibiMapper monthGwanlibiMapper) {
//		monthGwanlibiMapper = this.monthGwanlibiMapper;
//	}
	
	@Override
	public List<MonthGwanlibiVO> monGwanlibiList(String imdaeinId) {
		return monthGwanlibiMapper.selectAllMonGwanlibiList(imdaeinId);
	}

}