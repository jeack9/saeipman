package com.saeipman.app.noticeBuilding.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.noticeBuilding.service.NoticeBuildingService;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;

@Controller
public class NoticeBuildingController {
	private NoticeBuildingService noticeBuildingService;
	
	@Autowired
	public NoticeBuildingController(NoticeBuildingService noticeBuildingService) {
		this.noticeBuildingService = noticeBuildingService;
	}
	
		// 전체조회(noticeBuildingList)
		@GetMapping("noticeBuildingList")
		public String noticeBuildingList(Model model) {
			List<NoticeBuildingVO> list = noticeBuildingService.noticeBuildingList();
			model.addAttribute("BNotice", list);
			return "noticeBuilding/noticeBuildingList";
		}
	
		// 단건조회
		@GetMapping("noticeBuildingInfo")
		
		public String noticeBuildingInfo(NoticeBuildingVO noticeBuildingVO, Model model) {
			NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
			model.addAttribute("BNotice", selectVO);
			return "noticeBuilding/noticeBuildingInfo";
		}
		
		// 등록(페이지)
		@GetMapping("noticeBuildingInsert")
		public String noticeBuildingInsertForm() {
			return "noticeBuilding/noticeBuildingInsert";
		}
		
		// 등록(처리)
		@PostMapping("noticeBuildingInsert")
		public String noticeBuildingProc(NoticeBuildingVO noticeBuildingVO) {
			int no = noticeBuildingService.noticeBuildingInsert(noticeBuildingVO);
			return "redirect:noticeBuildingInfo?postNo="+ no;
		}
		
		// 수정(페이지)
		@GetMapping("noticeBuildingUpdate")
		public String noticeBuildingUpdateForm(NoticeBuildingVO noticeBuildingVO, Model model) {
			NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
			model.addAttribute("BNotice",selectVO);
			return "noticeBuilding/noticeBuildingUpdate";
		}
		
		// 수정(처리)
		@PostMapping("noticeBuildingUpdate")
		@ResponseBody
		public Map<String, Object> noticeBuildingUpdateJson(@RequestBody NoticeBuildingVO noticeBuildingVO) {
			return noticeBuildingService.noticeBuildingUpdate(noticeBuildingVO);
		}
		
		// 삭제(처리)
		@GetMapping("noticeBuildingDelete")
		public String noticeBuildingDelete(Integer no) {
			noticeBuildingService.noticeBuildingDelete(no);
			return "redirect:noticeBuildingList";
		}
}
