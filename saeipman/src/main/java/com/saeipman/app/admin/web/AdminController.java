package com.saeipman.app.admin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.admin.Service.AdminService;
import com.saeipman.app.admin.Service.Member;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.file.service.FileVO;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;
	private final FileService fileService;
	private final FileUtility fileUtill;
	@GetMapping("testc")
	public String getMethodName() {
		return "ok";
	}

	// 멤버 목록 조회
	@GetMapping("/memberList/{auth}")
	public List<Member> memberList(@PathVariable(name = "auth") int auth) {
		System.out.println("auth" + auth);
		if (auth == 1) {
			return adminService.imdaeinList();
		} else {
			return adminService.imchainList();
		}
	}

	@DeleteMapping("/membersRemove")
	public String membersRemove() {
		return "ok";
	}

	// 공지사항 목록 조회 (페이징 및 검색)
	@GetMapping("/notice")
	public Map<String, Object> getNotices(
			@RequestParam(defaultValue = "", name = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "10", name = "recordSize") int recordSize) {
		int total = adminService.countNotices(keyword);
		PagingDTO paging = new PagingDTO(page, recordSize, total, 5);
		List<NoticeVO> notices = adminService.noticeList(paging, keyword);

		Map<String, Object> map = new HashMap<>();
		map.put("notices", notices);
		map.put("paging", paging);
		map.put("keyword", keyword);
		return map;
	}

	// 공지사항 단건조회
	@GetMapping("/notice/{postNo}")
	public NoticeVO getNotice(@PathVariable(name = "postNo") Integer postNo) {
		System.out.println("no" + postNo);
		NoticeVO notice = adminService.noticeInfo(postNo);
		// 사진 없으면 빈 배열 주입
		if(notice.getNoticeFiles() == null) {
			notice.setNoticeFiles(new ArrayList<FileVO>());
		}
		return notice;
	}

	// 공지사항 단건삭제
	@DeleteMapping("/notice/{postNo}")
	public void deleteNotice(@PathVariable(name = "postNo") Integer postNo) {
		adminService.removeNotice(postNo);
	}

	// 공지사항 단건등록
	@PostMapping("/notice")
	public Map<String, Object> insertNotice(@ModelAttribute NoticeVO noticeVO, @RequestParam(value = "files", required = false) MultipartFile[] files) {
		fileUtill.setFolder("관리자공지");
		String groupId = fileUtill.multiUpload(files, "-1");
		noticeVO.setGroupId(groupId);
		
		return adminService.addNotice(noticeVO);
	}
	
	// 공지사항 단건수정
	@PutMapping("/notice/{postNo}")
	public Map<String, Object> modiNotice(@PathVariable(name = "postNo") Integer postNo, @ModelAttribute NoticeVO noticeVO, @RequestParam(value = "files", required = false) MultipartFile[] files) {
		NoticeVO prevNotice = adminService.noticeInfo(postNo);
		prevNotice.setTitle(noticeVO.getTitle());
		prevNotice.setContent(noticeVO.getContent());
		prevNotice.setModWriter(noticeVO.getModWriter());
		System.err.println(prevNotice.getGroupId());
		fileUtill.setFolder("관리자공지");
		fileUtill.multiUpload(files, prevNotice.getGroupId());
		return adminService.modiNotice(prevNotice);
	}
	
	// 파일 삭제
	@DeleteMapping("/file/{fileId}")
	public String removeFiles(@PathVariable(name = "fileId") Integer fileId) {
		FileVO vo = fileService.getFileInfo(fileId);
		fileUtill.deleteFile(vo.getFilePath());
		fileService.removeFile(fileId);
		return "ok";
	}

}
