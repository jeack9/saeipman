package com.saeipman.app.minwon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.minwon.mapper.MinwonMapper;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;
import com.saeipman.app.minwon.service.PageDTO;



@Service
public class MinwonServiceImpl implements MinwonService{

	private MinwonMapper minwonMapper;
	
	@Autowired
	public MinwonServiceImpl(MinwonMapper minwonMapper) {
		this.minwonMapper = minwonMapper;
	}

	@Override
	public List<MinwonVO> minwonList(Criteria cri) {
		return minwonMapper.selectMinwonAll(cri);
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
	public Map<String, Object> minwonUpdate(MinwonVO minwonVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = minwonMapper.updateMinwon(minwonVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateDate = sdf.format(today);
		
		map.put("date", updateDate);
		map.put("result", isSuccessed);
		map.put("target", minwonVO);
		return map;
	}

	@Override
	public int minwonDelete(int postNO) {
		return minwonMapper.deleteMinwon(postNO);
	}

	@Override
	public List<MinwonVO> categoryList() {
		return minwonMapper.selectCategory();
	}

	@Override
	public int pageTotal(Criteria cri) {
		return minwonMapper.getTotalCount(cri);
	}
	
	
}
