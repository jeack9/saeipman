package com.saeipman.app.find.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;



@Controller

public class findController {
	private FindService findService;
	
	public findController(FindService findService) {
		this.findService = findService;
	}
	
	@GetMapping("/all/idSelect")
	public String findId() {
		return "find/id";
	}
	
	//아이디 찾기
	@RequestMapping(value = "/all/idfind", method = RequestMethod.GET)
	@ResponseBody
	public String idSelect(@RequestParam(value = "imdaeinName", required = false) String imdaeinName, 
            @RequestParam(value = "imdaeinEmail", required = false) String imdaeinEmail) {
	    FindVO findVO = new FindVO();
	    findVO.setImdaeinName(imdaeinName);
	    findVO.setImdaeinEmail(imdaeinEmail);

	    FindVO fVO = findService.idSelect(findVO);

	    if (fVO != null && fVO.getImdaeinId() != null) {
	        return fVO.getImdaeinId();  // 조회한 아이디 반환
	    } else {
	        return "0";  // 해당 사용자를 찾지 못한 경우
	    }
	}
	
	
	
}
