package com.saeipman.app.building.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BuildingController {
	private BuildingService buildingService;

	public BuildingController(BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	// 파일 업로드
	@Value("${file.upload.path}")
	private String uploadPath;

	@GetMapping("/buildingList")
	public String buildingInfo(Model model) {
		List<BuildingVO> list = buildingService.buildingDetail();
		model.addAttribute("buildings", list);

		return "building/buildingList";
	}

	@GetMapping("/buildingDetails")
	@ResponseBody
	public BuildingVO buildingDetails(@RequestParam("id") String buildingId) {
		BuildingVO buildingVO = new BuildingVO();

		buildingVO.setBuildingId(buildingId);
		return buildingService.buildingInfo(buildingVO);
	}

	@GetMapping("/buildingInsert")
	public String insertBuildingForm() {
		return "building/buildingInsert";
	}

	/*
	 * @PostMapping("/buildingInsert") public String insertBuilding(BuildingVO
	 * buildingVO) { int success = buildingService.insertBuilding(buildingVO);
	 * 
	 * return "redirect:buildingList"; }
	 */

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

	@PostMapping("/buildingInsert")
	public String insertBuilding(@RequestPart MultipartFile[] files, BuildingVO buildingVO) {
		List<String> imgList = new ArrayList<>();
		
		log.info(uploadPath);
		
		for (MultipartFile file : files) {
			log.info(file.getContentType());
			log.info(file.getOriginalFilename());
			log.info(String.valueOf(file.getSize()));

			String fileName = file.getOriginalFilename();
			String saveName = uploadPath + File.separator + fileName; // separator = 자바가 인식하는 경로

			log.debug("saveName : " + saveName);

			Path savePath = Paths.get(saveName);

			try {
				file.transferTo(savePath);
				imgList.add(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		buildingVO.setBuildingImage(String.join(",", imgList));
		int success = buildingService.insertBuilding(buildingVO);
		return "redirect:buildingList";
	}

}
