package com.saeipman.app.building.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.saeipman.app.building.service.MonthRentService;
import com.saeipman.app.room.service.RentPayVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MonthRentListController {
	//private final MonthRentService monthService;
	
	@GetMapping("/monthPaymentHistory")
	public String monthRentList(RentPayVO payVO) {
		
		return "building/monthRentList";
	}
}
