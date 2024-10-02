package com.saeipman.app.payment.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	// 납부 페이지
	@GetMapping("paymentInfo")
	public String paymentInfoPage(PaymentVO payVO, Model model) {
		int payYN = payVO.getPaymentYN();
		int payState = payVO.getPaymentState();
		String loginId = SecurityUtil.getLoginId();

		payVO.setImchainPhone(loginId);

		payVO.setPaymentYN(payYN);
		payVO.setPaymentState(payState);
		

		Map<String, Object> payInfo = paymentService.selectPayInfo(payVO);
		model.addAttribute("payInfo", payInfo);

		return "payment/paymentInfo";
	}

	// 기간에 따른 납부 조회
	@GetMapping("paymentAjax")
	@ResponseBody
	public Map<String, Object> paymentInfo(@RequestParam("payDate") String payDate, PaymentVO payVO) {
		
		System.out.println("ddd : " + payDate);

		String loginId = SecurityUtil.getLoginId();

		// PaymentVO paymentVO = new PaymentVO();
		payVO.setPaymentYN(1);
		payVO.setPaymentMonth(payDate);
		payVO.setPaymentState(1);
		payVO.setRealPaymentDate(payDate);
		payVO.setImchainPhone(loginId);
		System.out.println(payVO+"ddddddd");
		Map<String, Object>  pay = paymentService.selectPayInfo(payVO);
		if (pay == null) {
			return null;
		}
		return pay;
	}

	// 납부 처리
	@PostMapping("updateAjax")
	@ResponseBody
	public String updateAjax(@RequestBody List<PaymentVO> payments) {
		System.out.println(payments.toString()+"wwwwwwwwwwwwwwwwwwwwwwwww");
		for (PaymentVO payment : payments) {
			payment.getRealPaymentDate();
			payment.getRealPaymentMoney();
//			System.out.println(payment.getGaguPaymentHistoryNo() + "가구히스토리");
//			System.out.println(payment.getmRentHistoryNo() + "월세 히스토리!!!!");
			paymentService.updatePaymentStatus(payment);
		}
		return "OK";
	}

}
