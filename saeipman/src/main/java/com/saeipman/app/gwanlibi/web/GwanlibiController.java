package com.saeipman.app.gwanlibi.web;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class GwanlibiController {

	private GwanlibiService gwanlibiService;
	
	// 로그인한 사용자(임대인)의 건물들을 목록으로 출력하고, 해당 건물의 전월, 금월 관리비 출력 화면으로 이동
	// TODO : 세션 or 쿠키에서 로그인 값 가져오기
	@GetMapping("gwanlibiList")
	public String gwanlibiList(Model model, @RequestParam(defaultValue = "test01") String imdaeinId) {
		List<GwanlibiVO> gwanlibiList = gwanlibiService.monthGwanlibiByBuildingList(imdaeinId);

		model.addAttribute("list", gwanlibiList);

		return "gwanlibi/gwanlibiList";
	}
	
		
	// 건물별 관리비 상세 내역 출력 화면으로 이동
	@GetMapping("detailsBillList")
	public String detailsBillList(Model model, GwanlibiVO vo) {
		Date now = new Date();
		vo.setPaymentMonth(now);
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(vo);
		
		
		model.addAttribute("detailsList", detailsList);
		
		
		return "gwanlibi/detailsBillList";
	}

//	@PostMapping("detailsBillList")
//	public String detailsBillList() {
//		return null;		
//	}

}