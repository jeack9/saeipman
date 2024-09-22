package com.saeipman.app.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saeipman.app.admin.Service.AdminService;
import com.saeipman.app.admin.Service.Member;
import com.saeipman.app.admin.Service.NoticeVO;
import com.saeipman.app.commom.paging.PagingDTO;

import lombok.RequiredArgsConstructor;


@Controller
@RestController
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/api/memberList/{auth}")
	public List<Member> memberList(@PathVariable int auth) {
		System.out.println("auth" + auth);
		if(auth == 1) {
			return adminService.imdaeinList();
		}else {
			return adminService.imchainList();
		}
	}
	@DeleteMapping("/api/membersRemove")
	public String membersRemove() {
		
		return "ok";
	}
	
	
	// 공지사항 목록 조회 (페이징 및 검색)
    @GetMapping("/api/notice")
    public Map<String, Object> getNotices(@RequestParam(name="keyword", defaultValue = "") String keyword,
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
    @GetMapping("/api/notice/{postNo}")
    public NoticeVO getNotice(@PathVariable(name = "postNo") Integer postNo) {
    	System.out.println("no" + postNo);
    	NoticeVO notice = adminService.noticeInfo(postNo);
        return notice;
    }
    
    // 공지사항 단건삭제
    @DeleteMapping("/api/notice/{postNo}")
    public void deleteNotice(@PathVariable(name = "postNo") Integer postNo) {
    	adminService.removeNotice(postNo);
    }
    
}
