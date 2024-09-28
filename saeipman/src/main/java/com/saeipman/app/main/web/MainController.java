package com.saeipman.app.main.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.main.service.MainService;
import com.saeipman.app.payment.service.PaymentVO;

@Controller
public class MainController {
	private MainService mainService;

	@Autowired
	public MainController(MainService mainService) {
		this.mainService = mainService;
	}

	@GetMapping("mainInfo")

	public String MainSelectPage(PaymentVO payVO, Model model) {

		String loginId = SecurityUtil.getLoginId();

		payVO.setImdaeinId(loginId);
		payVO.setConstractState(1);
		payVO.setPaymentState(-1);

		//Integer constractInfo = mainService.selectConstractState(payVO);
		//Integer unPaymentInfo = mainService.unPaymentState(payVO);

		//model.addAttribute("constractInfo", constractInfo);
		//model.addAttribute("unPaymentInfo", unPaymentInfo);

		return "main/mainInfo";
	}


}
