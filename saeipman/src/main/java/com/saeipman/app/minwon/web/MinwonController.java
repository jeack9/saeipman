package com.saeipman.app.minwon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.saeipman.app.minwon.service.MinwonService;

@Controller
public class MinwonController {
	private MinwonService minwonService;
	
	@Autowired
	public MinwonController(MinwonService minwonService) {
		this.minwonService = minwonService;
	}
	
	// 전체조회
	
	// 단건조회
	
	// 등록(페이지)
	
	// 등록(처리)
	
	// 수정(페이지)
	
	// 수정(처리)
	
	// 삭제(처리)
}
