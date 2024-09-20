package com.saeipman.app.find.web;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;
import com.saeipman.app.message.MsgService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;



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

	@Autowired
	private MsgService msgService;
	
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
	
	//비번 찾기
	@GetMapping("/all/pwFind")
	public String findpw() {
		return "find/pwFind";
	}
	
	//인증번호 발송 및 인증번호 저장
	@PostMapping("/all/sendSMS")
	@ResponseBody
	public String sendSms(@RequestParam(value="phone", required = false) String phone,
			HttpSession session){
		
		Random rand = new Random();
		String numStr = "";
		
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr += ran;
		}
		System.out.println("인증번호 : " + numStr);
		session.setAttribute(phone, numStr);
		
		String msg = "아래의 인증번호를 입력해주세요\n " + numStr;
		//msgService.sendOne(phone, msg);
		return numStr;
		
	}
	
	//인증번호 확인
	@PostMapping("/all/smsCheck")
	@ResponseBody
	public ResponseEntity<Boolean> smsCheck(@RequestParam(name="phone", required = false) String phone,
						   @RequestParam(name="inputCode", required = false) String numStr,
						   HttpSession session) {
		System.out.println(phone + " phone");
		String storedCode = (String) session.getAttribute(phone);
		System.out.println(storedCode + "세션코드");
		if(storedCode != null && storedCode.equals(numStr)) {
			return ResponseEntity.ok(true);
		}else {
			return ResponseEntity.ok(false);
		}
		
	}
	
	
	//비밀번호 찾기 요청시 입력받은 값 넘기기
	@PostMapping("/all/pwUpform")
	@ResponseBody
	public FindVO pwUpform(@ModelAttribute FindVO findVO) {
		
		FindVO check = findService.pwSelect(findVO);
		System.out.println(check);
		
		return check;
	}
	
}
