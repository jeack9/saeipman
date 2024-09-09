package com.saeipman.app.building.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BuildingController {
	private final BuildingService buildingService;
	private final FileUtility fileUtill;
	private final FileService fileService;

	@GetMapping("/buildingList")
	public String buildingInfo(BuildingPageDTO buildingPageDTO,Model model) {
		
		//리스트 총 수
		int total = buildingService.totalPage(buildingPageDTO);
		buildingPageDTO.setTotal(total);
		
		//리스트 출력
		List<BuildingVO> list = buildingService.buildingDetail(buildingPageDTO);
		
		model.addAttribute("buildings", list);
		
		model.addAttribute("page",buildingPageDTO);
		
		return "building/buildingList";
	}

	@GetMapping("/buildingDetails")
	@ResponseBody
	public BuildingVO buildingDetails(@RequestParam("id") String buildingId) {
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBuildingId(buildingId);

		BuildingVO result = buildingService.buildingInfo(buildingVO);

		List<String> fileName = fileService.getFileName(buildingVO.getBuildingId());
		System.out.println("파일" + fileName);
		if (result == null) {
			return result;
		}

		result.setFileName(fileName);

		return result;
	}

	@GetMapping("/buildingInsert")
	public String insertBuildingForm() {
		return "building/buildingInsert";
	}

	@PostMapping("/buildingInsert")
	public String insertBuilding(@RequestParam("file") MultipartFile file, @RequestPart MultipartFile[] files, BuildingVO buildingVO) {
		fileUtill.setFolder("건물");
		String groupId = fileUtill.upload(files);

		buildingVO.setGroupId(groupId);
		

		int success = buildingService.insertBuilding(buildingVO);
		return "redirect:buildingList";
	}

	@PostMapping("/buildingUpdate")
	@ResponseBody
	public Map<String, Object> updateBuilding(@RequestBody BuildingVO buildingVO) {
		System.out.println(buildingVO);

		return buildingService.updateBuilding(buildingVO);
	}

	@GetMapping("/buildingDelete")
	public String buildingDelete(@RequestParam("id") String buildingId) {
		buildingService.buildingDelete(buildingId);
		return "redirect:buildingList";
	}

}
