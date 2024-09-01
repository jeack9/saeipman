package com.saeipman.app.minwon.mapper;

import java.util.List;

import com.saeipman.app.minwon.service.MinwonVO;

public interface MinwonMapper {

	//전체조회
	public List<MinwonVO> selectMinwonAll();
	
	//단건조회
	public MinwonVO selectMinwonInfo(MinwonVO minwonVO);
	
	//등록(postNo, title, content, category, chumbuimage, roomNo, visitsDate)
	public int insertMinwon(MinwonVO minwonVO);
	
	//수정(title, content, category, chumbuImage, visitsDate)
	public MinwonVO updateMinwon(MinwonVO minwonVO);
	
	//삭제
	public int deleteMinwon(int postNO);
}
