package com.saeipman.app.gwanlibi.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saeipman.app.gwanlibi.mapper.MonthGwanlibiMapper;
import com.saeipman.app.gwanlibi.service.MonthGwanlibiService;
import com.saeipman.app.gwanlibi.service.MonthGwanlibiVO;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
public class MonthGwanlibiController {
	
	private MonthGwanlibiService monthGwanlibiService;
	
	//TODO : 세션 or 쿠키에서 로그인 값 가져오기
	@GetMapping("gwanlibiList")
	public String gwanlibiList(Model model, @RequestParam(defaultValue = "10") String imdaeinId) {
		List<MonthGwanlibiVO> gwanlibiList = monthGwanlibiService.monGwanlibiList(imdaeinId);
		
		model.addAttribute("list", gwanlibiList);
		
		return "gwanlibi/gwanlibiList";
	}
	
}