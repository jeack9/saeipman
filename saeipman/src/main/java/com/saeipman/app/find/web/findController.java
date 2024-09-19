package com.saeipman.app.find.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;



@Controller

public class findController {
	private FindService findService;
	
	public findController(FindService findService) {
		this.findService = findService;
	}
	
	//아이디 찾기
	@RequestMapping(value = "/all/idSelect", method = RequestMethod.GET)
	public String idSelect(FindVO findVO, Model model) {
		FindVO fVO = findService.idSelect(findVO);
		model.addAttribute("idfind", fVO);
		
		
		return "find/id";
	}
	
	
	
}
