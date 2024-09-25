package com.saeipman.app.noticeBuilding.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingService;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;
import com.saeipman.app.noticeBuilding.utils.PagingSearchDTO;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 불러오는 어노테이션
@Controller
@RequiredArgsConstructor
public class NoticeBuildingController {
	private final NoticeBuildingService noticeBuildingService;
	private final FileUtility fileUtility;
	private final FileService fileService;

	@Value("${file.upload.path}") // 경로(file.upload.path)를 uploadPath 변수에 할당함.
	private String uploadPath;
	

	// 전체조회
	@GetMapping("noticeBuildingList")
	public String noticeBuildingList(PagingSearchDTO pgsc, Model model) {
		
		int auth = SecurityUtil.getLoginAuth();	
		
		//로그인한 권한에 따라 임대인/임차인 페이지가 다르게 보여야함. 1:임대인, 2:임차인
		if(auth == 1) { //로그인한 권한이 1인 경우(임대인)
			
			//securityutil에서 임대인id를 받아와서 변수에 할당
			String loginId = SecurityUtil.getLoginId();
			
			//pgsc의 임대인id에 받아온 속성값 부여 => 그래야 해당 임대인이 가진 건물들의 공지사항을 확인할 수 있음.
			pgsc.setImdaeinId(loginId);
		 
			//임대인이 소유한 건물의 이름을 확인하기 위해 buildingVO에도 속성값 부여
			BuildingVO buildingVO = new BuildingVO();
			buildingVO.setImdaeinId(loginId);
			List<BuildingVO> name = noticeBuildingService.imdaeinBuilding(buildingVO);
			model.addAttribute("Bname", name);
			
		} else { //로그인한 권한이 2인 경우(임차인) => 임차인은 본인이 거주하는 빌딩의 Id와 동일한 건물ID의 공지글만 확인하기 때문에.
			String buildingId = SecurityUtil.getBuildingId();
			pgsc.setBuildingId(buildingId);
		}
		
		List<NoticeBuildingVO> list = noticeBuildingService.noticeBuildingList(pgsc);
		model.addAttribute("BNotice", list);

		//전체 페이지 수 계산해서 setTotalPage에 전체 페이지 수 할당 
		int totalPage = noticeBuildingService.totalPage(pgsc);
		pgsc.setTotal(totalPage);
		model.addAttribute("Paging", pgsc);
		
		return "noticeBuilding/noticeBuildingList"; 
	}

	// 단건조회
	@GetMapping("noticeBuildingInfo")
	public String noticeBuildingInfo(NoticeBuildingVO noticeBuildingVO, PagingSearchDTO pgsc, Model model) {
		
		noticeBuildingService.noticeBuildingViews(noticeBuildingVO);
		
		NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
		
		List<String> fileName = noticeBuildingService.getFileInfo(noticeBuildingVO.getPostNo());
		
		selectVO.setFileName(fileName);
		
		model.addAttribute("BNotice", selectVO);
		model.addAttribute("pgsc", pgsc);
		
		return "noticeBuilding/noticeBuildingInfo";
	}

	// 등록(페이지)
	@GetMapping("noticeBuildingInsert")
	public String noticeBuildingInsertForm(NoticeBuildingVO noticeBuildingVO, Model model) {
		
		String loginId = SecurityUtil.getLoginId();
		
		LoginInfoVO login = SecurityUtil.getLoginInfo();
		model.addAttribute("login", login);
		
		noticeBuildingVO.setImdaeinId(loginId);
		
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setImdaeinId(loginId);
		List<BuildingVO> name = noticeBuildingService.imdaeinBuilding(buildingVO);
		model.addAttribute("Bname", name);
		
		
		return "noticeBuilding/noticeBuildingInsert";
	}

	// 등록(처리) + 파일 업로드(처리)
	@PostMapping("noticeBuildingInsert")
	public String noticeBuildingProc(@RequestPart MultipartFile[] files, NoticeBuildingVO noticeBuildingVO, Model model) {

		String loginId = SecurityUtil.getLoginId();

		noticeBuildingVO.setImdaeinId(loginId);
		
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setImdaeinId(loginId);
		
		List<BuildingVO> bname = noticeBuildingService.imdaeinBuilding(buildingVO);
		model.addAttribute("BuildingList", bname);
		
		
		//업로드 경로 폴더
		fileUtility.setFolder("공지사항");
		
		
		String groupId = fileUtility.multiUpload(files, "-1");
		
		noticeBuildingVO.setGroupId(groupId);
		int no = noticeBuildingService.noticeBuildingInsert(noticeBuildingVO);
		
		return "redirect:noticeBuildingInfo?postNo=" + no;
	}

	// 수정(페이지)
	@GetMapping("noticeBuildingUpdate")
	public String noticeBuildingUpdateForm(NoticeBuildingVO noticeBuildingVO, Model model) {
		
		String loginId = SecurityUtil.getLoginId();
		
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setImdaeinId(loginId);
		List<BuildingVO> name = noticeBuildingService.imdaeinBuilding(buildingVO);
		model.addAttribute("Bname", name);
		
		NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
		model.addAttribute("BNotice", selectVO);
		return "noticeBuilding/noticeBuildingUpdate";
	}

	// 수정(처리)
	@PostMapping("noticeBuildingUpdate")
	@ResponseBody
	public Map<String, Object> noticeBuildingUpdate(@RequestBody NoticeBuildingVO noticeBuildingVO) {
		
		//fileUtill.set
		String loginId = SecurityUtil.getLoginId();

		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setImdaeinId(loginId);
		
		
		return noticeBuildingService.noticeBuildingUpdate(noticeBuildingVO);
			
	}


	// 삭제(처리)
	@GetMapping("noticeBuildingDelete")
	public String noticeBuildingDelete(Integer no) {
		noticeBuildingService.noticeBuildingDelete(no);
		return "redirect:noticeBuildingList";
	}
	
	
	
	
}
