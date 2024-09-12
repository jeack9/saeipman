package com.saeipman.app.payment.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		payVO.setPaymentYN(-1); 
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
	
	
	
	
}
