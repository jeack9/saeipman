package com.saeipman.app.building.web;

import java.security.Security;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.building.service.MonthRentService;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.room.service.RentPayVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MonthRentListController {
	private final MonthRentService monthService;
	
	@GetMapping("/monthPaymentHistory")
	public String monthRentList(BuildingPageDTO buildingPageDTO, Model model) {
		String loginId = SecurityUtil.getLoginId();
		//buildingPageDTO.setBuildingId(loginId);
		//리스트 총수
		int totalPage = monthService.totalPage(loginId,buildingPageDTO );
		buildingPageDTO.setTotal(totalPage);
		
		model.addAttribute("page", buildingPageDTO);
		//건물 명 리스트
		List<RentPayVO> buildingNameList = monthService.buildingNameList(loginId);
		System.out.println(buildingNameList + "건물명 리스트");
		model.addAttribute("buildingNameList",buildingNameList);
		
		//월세 리스트
		List<RentPayVO> monthRentList = monthService.mRentList(buildingPageDTO, loginId);
		model.addAttribute("monthRentList",monthRentList);
		System.out.println(monthRentList+"monthRentList");
		
		return "building/monthRentList";
	}
}
