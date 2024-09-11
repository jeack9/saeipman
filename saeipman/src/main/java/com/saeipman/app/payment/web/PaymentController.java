package com.saeipman.app.payment.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.saeipman.app.payment.service.PaymentService;
import com.saeipman.app.payment.service.PaymentVO;

@Controller
public class PaymentController {
	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	//기간에 따른 납부 조회
	
	@GetMapping("paymentInfo")
	public String paymentInfo(PaymentVO payVO, Model model) {
		
		PaymentVO payInfo = paymentService.selectMonthInfo(payVO);
		model.addAttribute("payInfo", payInfo);
		
		return "Payment/paymentInfo";
	}
	
	
	
	
	
}
