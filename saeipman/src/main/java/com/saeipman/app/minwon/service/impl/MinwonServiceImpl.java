package com.saeipman.app.minwon.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saeipman.app.message.MessageVO;
import com.saeipman.app.message.MsgService;
import com.saeipman.app.message.mapper.MsgMapper;
import com.saeipman.app.minwon.mapper.MinwonMapper;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;



@Service
public class MinwonServiceImpl implements MinwonService{
	@Autowired
	private MinwonMapper minwonMapper;
	
	@Autowired
	private MsgMapper msgMapper;

	@Autowired
	private MsgService msgService;
	

	
	
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
		if(result == 1) {
			//메세지
			String msg = msgMapper.selectMinwonMsg(0);
			//String msg = "새로운 민원이 등록되었습니다.";
			//전화번호 조회
			String phone = msgMapper.selectPhone(minwonVO.getRoomId());
			//문자전송
			//msgService.sendOne(phone, msg);
					
		}
		
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

	@Override
	public int acceptStateUpdate(MinwonVO minwonVO) {
		return minwonMapper.updateAcceptState(minwonVO);
	}

	@Override
	public List<MinwonVO> buildingSelect(Criteria cri) {
		return minwonMapper.selectBuilding(cri);
	}

	@Override
	public List<String> getFileName(int postNo) {
		return minwonMapper.selectFileName(postNo);
	}



	

	
}
