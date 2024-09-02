package com.saeipman.app.minwon.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;

@Controller
public class MinwonController {
	
	private MinwonService minwonService;
	
	@Autowired
	public MinwonController(MinwonService minwonService) {
		this.minwonService = minwonService;
	}
	
	// 전체조회
	@GetMapping("minwonList")
	public String minwonList(Model model) {
		List<MinwonVO> list = minwonService.minwonList();
		model.addAttribute("minwon", list);
		return "minwon/minwonList";
	}
	
	// 단건조회
	@GetMapping("minwonInfo")
	public String minwonInfo(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		model.addAttribute("minwon",findVO);
		return "minwon/minwonInfo";
	}
	 
	// 등록(페이지)
	@GetMapping("minwonInsert")
	public String minwonInsertForm() {
		return "minwon/minwonInsert";
	}
	// 등록(처리)
	@PostMapping("minwonInsert")
	public String minwonInsert(MinwonVO minwonVO) {
		minwonVO.setRoomId("2");
		minwonVO.setRoomNo("101");
		int no = minwonService.minwonInsert(minwonVO);
		String url = null;
		if(no > -1) {
			url = "redirect:minwonInfo?postNo="+no;
		}else {
			url = "redirect:minwonList";
		}
		return url;
	}
	// 수정(페이지)
	@GetMapping("minwonUpdate")
	public String boardUpdateForm(MinwonVO minwonVO, Model model) {
		//MinwonVO findVO = minwonService
		//model.addAttribute("minwon",findVO);
		return "minwon/minwonUpdate";
	}
	// 수정(처리)
	@PostMapping("minwonUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdate(MinwonVO minwonVO){
		return minwonService.minwonUpdate(minwonVO);
	}
	
	// 삭제(처리)
	@GetMapping("minwonDelete")
	public String minwonDelete(Integer postNo) {
		minwonService.minwonDelete(postNo);
		return "redirect:minwonList";
	}
		
}
