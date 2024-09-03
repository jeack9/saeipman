package com.saeipman.app.gwanlibi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.gwanlibi.service.GwanlibiDetailsService;
import com.saeipman.app.gwanlibi.service.GwanlibiDetailsVO;

@Controller
public class GwanlibiDetailsController {

	private GwanlibiDetailsService gwanlibiDeailsService;

	@Autowired
	private GwanlibiDetailsController(GwanlibiDetailsService gwanlibiDeailsService) {
		this.gwanlibiDeailsService = gwanlibiDeailsService;
	}

	@GetMapping("detailsList")
//	public String detailsList(Model model, @RequestParam(name = "buildingId") String buildingId, @RequestParam(name = "selectedDate") String selectedDate) {
//		List<GwanlibiDetailsVO> detailsList = gwanlibiDeailsService.detailsList(buildingId, selectedDate);
	public String detailsList(Model model, @RequestParam(name = "buildingId") String buildingId) {
		List<GwanlibiDetailsVO> detailsList = gwanlibiDeailsService.detailsList(buildingId);
		
		model.addAttribute("list", detailsList);
		
		
		return "gwanlibi/detailsList";
	}

	@GetMapping("detailsListAjax")
	@ResponseBody
//	public String detailsList(Model model, @RequestParam(name = "buildingId") String buildingId, @RequestParam(name = "selectedDate") String selectedDate) {
//		List<GwanlibiDetailsVO> detailsList = gwanlibiDeailsService.detailsList(buildingId, selectedDate);
	public List<GwanlibiDetailsVO> detailsListAjax(@RequestParam(name = "buildingId") String buildingId) {
		List<GwanlibiDetailsVO> detailsList = gwanlibiDeailsService.detailsList(buildingId);
		
		return detailsList;
	}
}
