package com.saeipman.app.gwanlibi.web;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public String gwanlibiList(Model model, @RequestParam(name = "imdaeinId", defaultValue = "test01") String imdaeinId) {
		List<GwanlibiVO> list = gwanlibiService.monthGwanlibiByBuildingList(imdaeinId);

		model.addAttribute("list", list);

		return "gwanlibi/gwanlibiList";
	}

	// 건물별 관리비 상세 내역 출력 화면으로 이동
	@GetMapping("detailsBillList")
	public String detailsBillList(Model model, GwanlibiVO vo) {

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
	
	// 월별 건물별 관리비 상세 내역 출력 화면으로 이동- ajax
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
		
		return "gwanlibi/gwanlibiChargeInsertUpdatePage"; // 뷰 리졸버가 읽어서 처리 , html 경로
	}
	
	// 관리비 등록 - 아작스 (폼 아니고 버튼)
	@PostMapping("insertMaintenceCost")
	@ResponseBody
	public Map<String, Object> insertCoast(@RequestBody List<GwanlibiVO> list) {
		System.out.println("sdsds");
		for(GwanlibiVO vo : list) {
			System.out.println(vo.toString());
		}
		Map<String, Object> map = gwanlibiService.addMaintenanceCoast(list);
		return map;
	}

}