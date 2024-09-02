package com.saeipman.app.building.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;

@Controller
public class BuildingController {
	private BuildingService buildingService;
	
	public BuildingController(BuildingService buildingService) {
		this.buildingService = buildingService;
	}
	
	@GetMapping("/buildingList")
	public String buildingInfo(Model model) {
		List<BuildingVO> list = buildingService.buildingDetail();
		model.addAttribute("buildings",list);
		
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
	@PostMapping("/buildingInsert")
	public String insertBuilding(BuildingVO buildingVO) {
		//int success = buildingService.insertBuilding(buildingVO);
		
		return "redirect:buildingList";
	}
	@PostMapping("/buildingUpdate")
	@ResponseBody
	public Map<String, Object> updateBuilding(@RequestBody BuildingVO buildingVO){
		return buildingService.updateBuilding(buildingVO);
	}
}
