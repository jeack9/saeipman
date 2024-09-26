package com.saeipman.app.admin.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QnaController {
	private final FileUtility fileUtill;
	private final FileService fileService;
	private final QnaService qnaService;
	
	@GetMapping("qnaList")
	public String qnaList(Model model) {
		List<QnaVO> list = qnaService.qnaList();
		model.addAttribute("qna", list);
		return "qna/qnaList";
	}
}
