package com.saeipman.app.support.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saeipman.app.admin.Service.AdminService;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.admin.Service.QnaCmtVO;
import com.saeipman.app.admin.Service.QnaService;
import com.saeipman.app.admin.Service.QnaVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.support.service.SearchQna;
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
	private final QnaService qnaService;
	
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
		// 공지 조회
		NoticeVO notice = adminService.noticeInfo(postNo);
		adminService.increaseViews(postNo);
		// 사진 없으면 빈 배열 주입
		if(notice.getNoticeFiles() == null) {
			notice.setNoticeFiles(new ArrayList<FileVO>());
		}
		model.addAttribute("notice", notice);
	}
	
	// qna 목록 페이지이동
	@GetMapping("/qnaList")
	public void qnaListP(Model model, SearchQna search) {
		// 페이징과 검색조건
		search.setWriterId(SecurityUtil.getLoginId());
		search.setAuth(SecurityUtil.getLoginAuth());
		int page = search.getPage() < 1 ? 1 : search.getPage();
		int total = qnaService.totalQna(search);
		PagingDTO paging = new PagingDTO(page, 10, total, 10);
		
		model.addAttribute("paging", paging);
		model.addAttribute("search", search);
		model.addAttribute("qnaList", qnaService.qnaList(search));
	}
	
	// qna 등록 페이지이동
	@GetMapping("/qnaInsert")
	public void qnaInsertP() {
		
	}
	// qna 단건등록
	@PostMapping("/qnaInsert")
	public String qnaInsert(QnaVO qnaVO, @RequestPart(required = false) MultipartFile[] files) {
		fileUtill.setFolder("qna");
		String groupId = fileUtill.multiUpload(files, "-1");
		if(qnaVO.getWriter() == null || qnaVO.getWriter().length() == 0) {
			qnaVO.setWriter("익명");
		}
		qnaVO.setWriterId(SecurityUtil.getLoginId());
		qnaVO.setGroupId(groupId);
		qnaService.addQna(qnaVO);
		return "redirect:/support/qnaList";
	}
	
	// qna 단건조회 페이지이동
	@GetMapping("/qnaDetail")
	public void qnaDetailP(@RequestParam(name = "postNo", required = false) int postNo, Model model) {
		// qna단건, 댓글목록 조회
		int total = qnaService.totalParentCmts(postNo);
		PagingDTO paging = new PagingDTO(1, 2, total, 5);
		QnaVO qnaVO = qnaService.qnaInfo(postNo, paging);
		model.addAttribute("paging", paging);
		model.addAttribute("qna", qnaVO);
		
	}
	
	// 부모댓글 등록
	@PostMapping("/qnaParentCmt")
	public String qnaParentCmt(@RequestBody QnaCmtVO qnaCmtVO, Model model) {
		// 부모댓글 등록
		qnaCmtVO.setWriterId(SecurityUtil.getLoginId());
		qnaCmtVO.setAuth(SecurityUtil.getLoginAuth());
		qnaService.addParentCmt(qnaCmtVO);
		
		// 페이징, 댓글리스트 정보로 프래그먼트 반환
		int total = qnaService.totalParentCmts(qnaCmtVO.getPostNo());
		PagingDTO paging = new PagingDTO(1, 2, total, 5);
		QnaVO qnaVO = qnaService.qnaInfo(qnaCmtVO.getPostNo(), paging);
		model.addAttribute("paging", paging);
		model.addAttribute("qna", qnaVO);
		return "support/fragments/qnaPagination :: qnaPaginationFrg";
	}
	
	// 자식댓글 등록
	@PostMapping("/qnaChildCmt")
	@ResponseBody
	public QnaCmtVO qnaChildCmt(@RequestBody QnaCmtVO qnaCmtVO, Model model) {
		// 댓글등록
		int auth = SecurityUtil.getLoginAuth();
		qnaCmtVO.setWriterId(SecurityUtil.getLoginId());
		qnaCmtVO.setAuth(auth);
		QnaCmtVO cmt = qnaService.addChildCmt(qnaCmtVO);
		// auth 관리자면 qna 답변상태 변경
		return cmt;
	}
	
	// 댓글목록 + 페이지네이션 프레그먼트 반환
	@GetMapping("/loadQnaCmtsFrg")
	public String loadQnaCmtsFrg(@RequestParam(name = "page") int page, @RequestParam(name = "postNo") int postNo, Model model) {
		int total = qnaService.totalParentCmts(postNo);
		PagingDTO paging = new PagingDTO(page, 5, total, 5);
		QnaVO qnaVO = qnaService.qnaInfo(postNo, paging);
		model.addAttribute("paging", paging);
		model.addAttribute("qna", qnaVO);
		return "support/fragments/qnaPagination :: qnaPaginationFrg";
	}
	
	// 댓글삭제
	@DeleteMapping("/qnaCmt/{cmtNo}")
	@ResponseBody
	public QnaCmtVO removeQnaCmt(@PathVariable(name = "cmtNo", required = false) int cmtNo) {
		QnaCmtVO delCmt = new QnaCmtVO();
		try {
			delCmt.setCmtNo(cmtNo);
			delCmt.setWriterId(SecurityUtil.getLoginId());
			return qnaService.removeCmt(delCmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qnaService.cmtInfo(cmtNo);
	}
}
