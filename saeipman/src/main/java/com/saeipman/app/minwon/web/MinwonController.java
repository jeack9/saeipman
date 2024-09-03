package com.saeipman.app.minwon.web;



import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.minwon.service.MinwonService;
import com.saeipman.app.minwon.service.MinwonVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MinwonController {
	
	private MinwonService minwonService;
	
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	public MinwonController(MinwonService minwonService) {
		this.minwonService = minwonService;
	}
	
	// 전체조회
	@GetMapping("minwonList")
	public String minwonList(Model model) {
		List<MinwonVO> list = minwonService.minwonList();
		model.addAttribute("minwon", list);
		return "minwon/minwonList";
	}
	
	// 단건조회
	@GetMapping("minwonInfo")
	public String minwonInfo(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		model.addAttribute("minwon",findVO);
		return "minwon/minwonInfo";
	}
	
	// 등록(페이지)
	@GetMapping("minwonInsert")
	public String minwonInsertForm() {
	
		return "minwon/minwonInsert";
	}
	// 등록(처리)
	@PostMapping("minwonInsert")
	public String minwonInsert(@RequestPart MultipartFile[] files ,MinwonVO minwonVO) {
		minwonVO.setRoomId("2");
		minwonVO.setRoomNo("101");
		
		log.info(uploadPath);
		for(MultipartFile file : files) {
			log.info(file.getContentType());
			log.info(file.getOriginalFilename());
			log.info(String.valueOf(file.getSize()));
			
			String fileName = file.getOriginalFilename(); 
			String saveName = uploadPath + File.separator + fileName; //separator = 자바가 인식하는 경로
			
			log.debug("saveName : " + saveName);
			
			Path savePath = Paths.get(saveName); 
			
			try {
				file.transferTo(savePath);
				minwonVO.setChumbuImage(fileName);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		minwonService.minwonInsert(minwonVO);
		return "redirect:minwonList";
	}
	// 수정(페이지)
	@GetMapping("minwonUpdate")
	public String boardUpdateForm(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		model.addAttribute("minwon",findVO);
		return "minwon/minwonUpdate";
	}
	// 수정(처리)
	@PostMapping("minwonUpdate")
	@ResponseBody
	public Map<String, Object> boardUpdate(MinwonVO minwonVO){
		return minwonService.minwonUpdate(minwonVO);
	}
	
	// 삭제(처리)
	@GetMapping("minwonDelete")
	public String minwonDelete(Integer postNo) {
		minwonService.minwonDelete(postNo);
		return "redirect:minwonList";
	}
		
}
