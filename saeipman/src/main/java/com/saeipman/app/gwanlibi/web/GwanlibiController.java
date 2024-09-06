package com.saeipman.app.gwanlibi.web;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class GwanlibiController {

	private GwanlibiService gwanlibiService;

	// 로그인한 사용자(임대인)의 건물들을 목록으로 출력하고, 해당 건물의 전월, 금월 관리비 출력 화면으로 이동
	@GetMapping("gwanlibiList")
	public String gwanlibiList(Model model, @RequestParam(defaultValue = "test01") String imdaeinId) {
		List<GwanlibiVO> gwanlibiList = gwanlibiService.monthGwanlibiByBuildingList(imdaeinId);

		model.addAttribute("list", gwanlibiList);

		return "gwanlibi/gwanlibiList";
	}

	// 건물별 관리비 상세 내역 출력 화면으로 이동
	@GetMapping("detailsBillList")
	public String detailsBillList(Model model, GwanlibiVO vo) {
		//
		if (vo.getPaymentMonth() == null) {
			// 현재 날짜를 VO에 담아주기.
			Date now = new Date();
			vo.setPaymentMonth(now);
		}

		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(vo);

		// 가구별 관리비 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiByGagu();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiByGagu(result);
		}

		// 관리 비용 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiItemMoney();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiItemMoney(result);
		}

		model.addAttribute("detailsList", detailsList);
		model.addAttribute("buildingId", vo.getBuildingId());
		return "gwanlibi/detailsBillList";
	}
	
	// 월별 건물별 관리비 상세 내역 출력 화면으로 이동
	@PostMapping("detailsBillList")
	@ResponseBody
	public Map<String, Object> postMethodName(GwanlibiVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(vo);
		// 가구별 관리비 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiByGagu();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiByGagu(result);
		}

		// 관리 비용 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiItemMoney();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiItemMoney(result);
		}

		map.put("list", detailsList);
		return map;
	}
	
	// 건물별 관리비 상세 내역 출력 화면으로 이동 for 등록/수정 페이지
	@GetMapping("gwanlibiChargeInsertUpdatePage")
	public String insertUpdatePage(Model model, GwanlibiVO vo) {
		List<GwanlibiVO> list = gwanlibiService.detailsBillList(vo);
		
		model.addAttribute("list", list);
		
		return "gwanlibi/gwanlibiChargeInsertUpdatePage";
	}

}