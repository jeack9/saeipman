package com.saeipman.app.minwon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.minwon.mapper.MinwonMapper;
import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;

@Service
public class MinwonServiceImpl implements MinwonService{

	private MinwonMapper minwonMapper;
	
	@Autowired
	public MinwonServiceImpl(MinwonMapper minwonMapper) {
		this.minwonMapper = minwonMapper;
	}

	@Override
	public List<MinwonVO> minwonList() {
		return minwonMapper.selectMinwonAll();
	}

	@Override
	public MinwonVO minwonSelect(MinwonVO minwonVO) {
		return minwonMapper.selectMinwonInfo(minwonVO);
	}

	@Override
	public int minwonInsert(MinwonVO minwonVO) {
		int result = minwonMapper.insertMinwon(minwonVO);
		return result == 1? minwonVO.getPostNo() : -1;
	}

	@Override
	public MinwonVO minwonUpdate(MinwonVO minwonVO) {
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(today);
		
		return minwonMapper.updateMinwon(minwonVO);
	}

	@Override
	public int minwonDelete(int postNO) {
		return minwonMapper.deleteMinwon(postNO);
	}
	
	
}
