package com.saeipman.app.support.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.admin.Service.AdminService;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/support")
public class SupportController {
	private final FileUtility fileUtill;
	private final FileService fileService;
	private final AdminService adminService;

	@GetMapping("/qnaList")
	public void qnaList(Model model) {
	}

	// 공지사항 목록 조회 (페이징 및 검색)
	@GetMapping("/notices")
	public void getNotices(
			@RequestParam(defaultValue = "", name = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "10", name = "recordSize") int recordSize, Model model) {
		int total = adminService.countNotices(keyword);
		PagingDTO paging = new PagingDTO(page, recordSize, total, 5);
		List<NoticeVO> notices = adminService.noticeList(paging, keyword);

		model.addAttribute("notices", notices);
		model.addAttribute("paging", paging);
		model.addAttribute("keyword", keyword);
	}
	
	// 공지사항 단건조회
	@GetMapping("/noticeDetail")
	public void getNoticeDetail(Model model, @RequestParam(name = "postNo") Integer postNo) {
		NoticeVO notice = adminService.noticeInfo(postNo);
		// 사진 없으면 빈 배열 주입
		if(notice.getNoticeFiles() == null) {
			notice.setNoticeFiles(new ArrayList<FileVO>());
		}
		model.addAttribute("notice", notice);
	}
	
	// qna 등록 페이지이동
	@GetMapping("/qnaInsert")
	public void qnaInsertP() {
		
	}
	// qna 단건등록
	@PostMapping("/qnaInsert")
	public String postMethodName(@ModelAttribute QnaVO qnaVO , @RequestPart MultipartFile[] files) {
		
		return "";
	}
	

}
