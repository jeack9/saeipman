package com.saeipman.app.minwon.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.minwon.service.Criteria;
import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;
import com.saeipman.app.minwon.service.PageDTO;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MinwonController {

	private final MinwonService minwonService;
	private final FileUtility fileUtill;

	@Value("${file.upload.path}")
	private String uploadPath;

	

	// 전체조회
	@GetMapping("minwonList")
	public String minwonList(Criteria cri, Model model, MinwonVO minwonVO) {
		int auth = SecurityUtil.getLoginAuth();
		
		if(auth == 1) {
			String loginId = SecurityUtil.getLoginId();
			
			if(cri.getBuildingId() == null || cri.getBuildingId().isEmpty()) {
				minwonVO.setImdaeinId(loginId);
			}
			
			
		}
			
			
		
		
		
		String building = SecurityUtil.getBuildingId();
		cri.setBuildingId(building);
		
		String roomId = SecurityUtil.getRoomId();
		cri.setRoomId(roomId);
		//cri.setBuildingId("ZIP10"); //로그인한 임차인의 아이디(연락처)로 계약서의 방, 건물 아이디가 필요하다.
		cri.setAmount(5);
		List<MinwonVO> list = minwonService.minwonList(cri);
		model.addAttribute("minwon", list);
		int total = minwonService.pageTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		List<MinwonVO> buildingList = minwonService.buildingSelect();
		model.addAttribute("buildingList",buildingList );
		return "minwon/minwonList";
	}

	// 단건조회
	@GetMapping("minwonInfo")
	public String minwonInfo(@ModelAttribute Criteria cri, MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		
		List<String> fileName = minwonService.getFileName(minwonVO.getPostNo());
		findVO.setFileName(fileName);
		model.addAttribute("minwon", findVO);
		model.addAttribute("cri", cri);
		return "minwon/minwonInfo";
	}

	// 민원 상태 업로드
	@PostMapping("/updateMinwonState")
	public String updateMinwonState(@ModelAttribute Criteria cri, MinwonVO minwonVO,
			@RequestParam("state") String state) {
		// VO 객체에 상태값 세팅
		minwonVO.setAcceptState(state);

		// 서비스 계층을 통해 상태 업데이트
		minwonService.acceptStateUpdate(minwonVO);

		// 처리 후 민원 상세 페이지로 리다이렉트
		return "redirect:/minwonInfo?postNo=" + minwonVO.getPostNo() + "&pageNum=" + cri.getPageNum();
	}

	// 등록(페이지)
	@GetMapping("minwonInsert")
	public String minwonInsertForm(Model model) {
		List<MinwonVO> category = minwonService.categoryList();
		model.addAttribute("categories", category);
		return "minwon/minwonInsert";
	}

	// 등록(처리)
	@PostMapping("minwonInsert")
	public String minwonInsert(@RequestPart MultipartFile[] files, MinwonVO minwonVO) {
		
		fileUtill.setFolder("민원");//폴더명
		String groupId = fileUtill.multiUpload(files);
		String login = SecurityUtil.getLoginId();
		String roomId = SecurityUtil.getRoomId();
		minwonVO.setRoomId(roomId);
		minwonVO.setImchainId(login);
		minwonVO.setGroupId(login);
		minwonVO.setGroupId(groupId);
		
		minwonService.minwonInsert(minwonVO);
		return "redirect:minwonList";

	}


	// 수정(페이지)
	@GetMapping("minwonUpdate")
	public String minwonUpdateForm(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		
		model.addAttribute("minwon", findVO);
		List<MinwonVO> ca = minwonService.categoryList();
		model.addAttribute("categories", ca);
		return "minwon/minwonUpdate";

	}
	
	// 수정(처리)
	@PostMapping("minwonUpdate")
	@ResponseBody
	public Map<String, Object> updateList(@RequestBody MinwonVO minwonVO){
	    return minwonService.minwonUpdate(minwonVO);
	}

	// 삭제(처리)
	@GetMapping("minwonDelete")
	public String minwonDelete(Integer postNo) {
		minwonService.minwonDelete(postNo);
		return "redirect:minwonList";
	}

}
