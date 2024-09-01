package com.saeipman.app.minwon.service;

import java.util.List;

public interface MinwonService {
	//전체조회
	public List<MinwonVO> minwonList();
	
	//단건조회
	public MinwonVO minwonSelect(MinwonVO minwonVO);
	
	//등록
	public int minwonInsert(MinwonVO minwonVO);
	
	//수정
	public MinwonVO minwonUpdate(MinwonVO minwonVO);
	
	//삭제
	public int minwonDelete(int postNO);
	
}
