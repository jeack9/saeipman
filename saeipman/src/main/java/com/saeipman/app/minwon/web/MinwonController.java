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
	public String minwonList(@RequestParam(name = "buildingId", required = false) String buildingId,
			@RequestParam(name = "acceptState", required = false) Integer acceptState, Criteria cri, Model model) {
		int auth = SecurityUtil.getLoginAuth();
		String loginId = SecurityUtil.getLoginId();

		cri.setAuth(auth);
		cri.setLoginId(loginId);

		if (acceptState != null) {
			cri.setAcceptState(acceptState);
		}

		System.out.println("zjsxmfhffj");
		if (auth == 1) {
			System.out.println(buildingId + "buildingId");
			cri.setBuildingId(buildingId);

			cri.setImdaeinId(loginId);

			List<MinwonVO> buildingList = minwonService.buildingSelect(cri);
			model.addAttribute("buildingList", buildingList);
		} else if (auth == 2) {
			cri.setImchainId(loginId);
			cri.setBuildingId(buildingId);
		}

		// String roomId = SecurityUtil.getRoomId();
		// cri.setRoomId(roomId);
		// cri.setAmount(5);

		List<MinwonVO> list = minwonService.minwonList(cri);
		model.addAttribute("minwon", list);

		int total = minwonService.pageTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));

		model.addAttribute("cri", cri);

		return "minwon/minwonList";
	}

	// 단건조회
	@GetMapping("minwonInfo")
	public String minwonInfo(@ModelAttribute Criteria cri, MinwonVO minwonVO, Model model) {
		int auth = SecurityUtil.getLoginAuth();
		String loginId = SecurityUtil.getLoginId();

		cri.setAuth(auth);
		cri.setLoginId(loginId);

		MinwonVO findVO = minwonService.minwonSelect(minwonVO);

		List<String> fileName = minwonService.getFileName(minwonVO.getPostNo());
		findVO.setFileName(fileName);
		model.addAttribute("minwon", findVO);
		model.addAttribute("cri", cri);
		model.addAttribute("loginId", loginId);
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
	public String minwonInsert(@RequestPart(name = "files") MultipartFile[] files,@RequestParam(name = "buildingId", required = false) String buildingId, MinwonVO minwonVO) {

		fileUtill.setFolder("민원");// 폴더명
		String groupId = fileUtill.multiUpload(files, "-1");
		String login = SecurityUtil.getLoginId();
		String roomId = SecurityUtil.getRoomId();
		minwonVO.setRoomId(roomId);
		minwonVO.setImchainId(login);
		minwonVO.setGroupId(login);
		minwonVO.setGroupId(groupId);
		minwonVO.setBuildingId(buildingId);

		minwonService.minwonInsert(minwonVO);
		return "redirect:minwonList";

	}

	// 수정(페이지)
	@GetMapping("minwonUpdate")
	public String minwonUpdateForm(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		
		List<String> imageFiles = minwonService.getFileName((minwonVO.getPostNo()));
		findVO.setFileName(imageFiles);
		
		model.addAttribute("minwon", findVO);
		List<MinwonVO> ca = minwonService.categoryList();
		model.addAttribute("categories", ca);
		
		return "minwon/minwonUpdate";

	}

	// 수정(처리)
	@PostMapping("minwonUpdate")
	@ResponseBody
	public Map<String, Object> updateList(@ModelAttribute  MinwonVO minwonVO,
			@RequestPart(name = "newFiles", required = false) MultipartFile[] newFiles,
			@RequestParam(name = "deleteFileNames", required = false) List<String> deleteFileNames) {

		// 파일 삭제 처리
		if (deleteFileNames != null && !deleteFileNames.isEmpty()) {
			minwonService.fileDelete(deleteFileNames);
			for (String fileName : deleteFileNames) {
				fileUtill.deleteFile(fileName); // 실제 파일 삭제
				// buildingService.fileDelete(fileName);
			}
		}
		
		 if (newFiles != null && newFiles.length > 0) {
			 for (MultipartFile file : newFiles) {
				 System.out.println(file + "dssf");
			 }
		 }

		// 새 파일 업로드 처리
		String groupId = minwonVO.getGroupId(); // 기존 그룹 ID 가져오기

		// group_id가 없으면 새로 생성
		groupId = fileUtill.multiUpload(newFiles, groupId);
		minwonVO.setGroupId(groupId);

		return minwonService.minwonUpdate(minwonVO);
	}

	// 삭제(처리)
	@GetMapping("minwonDelete")
	public String minwonDelete(Integer postNo) {
		minwonService.minwonDelete(postNo);
		return "redirect:minwonList";
	}

}
