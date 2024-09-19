package com.saeipman.app.find.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.find.mapper.FindMapper;
import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;

@Service
public class FindServiceImpl implements FindService{
	@Autowired
	private FindMapper findMapper;
	
	@Override
	public FindVO idSelect(FindVO findVO) {
		return findMapper.selectId(findVO);
	}



}
