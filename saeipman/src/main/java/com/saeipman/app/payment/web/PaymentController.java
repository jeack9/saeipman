package com.saeipman.app.payment.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;
import com.saeipman.app.payment.service.PaymentService;
import com.saeipman.app.payment.service.PaymentVO;

@Controller
public class PaymentController {
	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	//납부 페이지
	@GetMapping("paymentInfo")
	public String paymentInfo(PaymentVO payVO, Model model) {
		
		int payYN = payVO.getPaymentYN(); 
		
		String loginId = SecurityUtil.getLoginId();
		
		payVO.setImchainPhone(loginId);
		
	      //model.addAttribute("login", loginId);
			
		payVO.setPaymentYN(-1); 
		payVO.setPaymentState(-1);
		List<PaymentVO> payInfo = paymentService.selectMonthInfo(payVO);
		model.addAttribute("payInfo", payInfo);
		
		return "Payment/paymentInfo";
	}
	
	//기간에 따른 납부 조회
	@GetMapping("paymentAjax")
	@ResponseBody
	public List<PaymentVO> paymentInfo(@RequestParam("pay") String paymentMonth) {
		PaymentVO paymentVO = new PaymentVO();
		paymentVO.setPaymentYN(1); 
		paymentVO.setPaymentMonth(paymentMonth);
		
		List<PaymentVO> payM = paymentService.selectMonthInfo(paymentVO);
		
		if (payM == null) {
			return null;
		}
		return payM;
	}
	
	//납부 처리
	@PostMapping("updateAjax")
	@ResponseBody
	public String updateAjax(@RequestBody List<PaymentVO> payments){
		for(PaymentVO payment : payments) {
//			System.out.println(payment.getGaguPaymentHistoryNo() + "가구히스토리");
			paymentService.updatePaymentStatus(payment);
		}
		return "OK";
	}
	
	
	
	
}
