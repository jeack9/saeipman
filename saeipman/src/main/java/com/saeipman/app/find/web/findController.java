package com.saeipman.app.find.web;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.find.mapper.FindMapper;
import com.saeipman.app.find.service.FindService;
import com.saeipman.app.find.service.FindVO;
import com.saeipman.app.message.MsgService;

import jakarta.servlet.http.HttpSession;



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
	
	
	//비밀번호 찾기 요청시 입력받은 값 넘기기(임대인)
	@PostMapping("/all/pwUpform")
	@ResponseBody
	public ResponseEntity<String> pwUpform(@ModelAttribute FindVO findVO, HttpSession session) {
		//입력된 정보로 회원 정보 조회
		FindVO check = findService.pwSelect(findVO);
		System.out.println(check);
		
		//회원 정보가 존재하는 경우 세션에 저장
		if(check != null) {
			session.setAttribute("imdaeinId", check.getImdaeinId());
			session.setAttribute("imdaeinName", check.getImdaeinName());
			//session.setAttribute("pw", check.getPw());
			// 성공 메시지로 "ok" 반환
	        return ResponseEntity.ok("ok");
		}else {
	        // 회원 정보를 찾을 수 없는 경우
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 회원 정보를 찾을 수 없습니다.");
	    }
		
		
	}
	
	//비밀번호 찾기 요청시 입력받은 값 넘기기(임차인)
	@PostMapping("/all/tenantPwUpform")
	@ResponseBody
	public ResponseEntity<String> tenantPwUpform(@ModelAttribute FindVO findVO, HttpSession session) {
	    // 입력된 정보로 임차인 정보 조회
	    FindVO check = findService.imchainPw(findVO);  // 임차인 정보 조회 서비스 호출
	    System.out.println(check);

	    // 임차인 정보가 존재하는 경우 세션에 저장
	    if (check != null) {
	        session.setAttribute("imchainId", check.getImchainId());  // 임차인 ID 저장
	        session.setAttribute("imchainName", check.getImchainName());  // 임차인 이름 저장
	        session.setAttribute("pw", check.getPw());
	        // 성공 메시지로 "ok" 반환
	        return ResponseEntity.ok("ok");
	    } else {
	        // 임차인 정보를 찾을 수 없는 경우
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 회원 정보를 찾을 수 없습니다.");
	    }
	}

	
	
	//비번 변경 페이지
	@GetMapping("all/pwUpdate")
	public String pwUpdate(){
		return "find/pwUpdate";
	}
	
	//비번 변경 처리
	@PostMapping("/all/changePw")
	@ResponseBody
	public ResponseEntity<String> changePassword(@RequestParam("newPassword") String newPassword,
	                                             HttpSession session) {
		
	    // 세션에서 loginId 가져오기
	    String loginId = (String) session.getAttribute("imdaeinId");
	    if (loginId == null) {
	        loginId = (String) session.getAttribute("imchainId");
	    }
	    
	    System.out.println("로그인 ID: " + loginId);  // 로그로 ID 확인
	    
	    if (loginId == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인된 사용자가 없습니다.");
	    }
	    
	    // 비밀번호 변경 요청 처리
	    FindVO findVO = new FindVO();
	    findVO.setLoginId(loginId);  // 로그인 ID 설정
	    findVO.setPw(newPassword);  // 새 비밀번호 설정

	    int rowsUpdated = findService.pwUpdate(findVO);  // Service 계층에서 처리
	    if (rowsUpdated > 0) {
	        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경에 실패했습니다.");
	    }
	}
	
}
