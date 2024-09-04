package com.saeipman.app.minwon.web;



import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	public String minwonInsertForm(Model model) {
		List<MinwonVO> category = minwonService.categoryList();
		model.addAttribute("categories", category);
		return "minwon/minwonInsert";
	}
	// 등록(처리)
	@PostMapping("minwonInsert")
	public String minwonInsert(@RequestPart MultipartFile[] files ,MinwonVO minwonVO) {
		minwonVO.setRoomId("2");
		minwonVO.setRoomNo("101");
		
		//tnejd
		List<String> imgList = new ArrayList<>();
		
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
				imgList.add(fileName);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
		}
		minwonVO.setChumbuImage(String.join(":", imgList));
		minwonService.minwonInsert(minwonVO);
		return "redirect:minwonList";
	}
	// 수정(페이지)
	@GetMapping("minwonUpdate")
	public String boardUpdateForm(MinwonVO minwonVO, Model model) {
		MinwonVO findVO = minwonService.minwonSelect(minwonVO);
		model.addAttribute("minwon",findVO);
		List<MinwonVO> ca = minwonService.categoryList();
		model.addAttribute("categories",ca);
		return "minwon/minwonUpdate";

		
	}
	// 수정(처리)
	@PostMapping("minwonUpdate")
	@ResponseBody
	public List<String> updatList(@RequestPart MultipartFile[] files, MinwonVO minwonVO){
		List<String> imageList = new ArrayList<>();
		
	    for(MultipartFile uploadFile : files){
	    	if(uploadFile.getContentType().startsWith("image") == false){
	    		System.err.println("this file is not image type");
	    		return null;
	        }
	  
	        String fileName = uploadFile.getOriginalFilename();
	        System.out.println("fileName : " + fileName);
	    
	        //날짜 폴더 생성
	        String folderPath = makeFolder(); //
	        //UUID
	        String uuid = UUID.randomUUID().toString();
	        //저장할 파일 이름 중간에 "_"를 이용하여 구분
	        
	        String uploadFileName = folderPath +File.separator + uuid + "_" + fileName;
	        
	        String saveName = uploadPath + File.separator + uploadFileName;
	        
	        Path savePath = Paths.get(saveName);
	        //Paths.get() 메서드는 특정 경로의 파일 정보를 가져옵니다.(경로 정의하기)
	        System.out.println("path : " + saveName);
	        try{
	        	uploadFile.transferTo(savePath);
	            //uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
	        } catch (IOException e) {
	             e.printStackTrace();	             
	        }
	        // DB에 해당 경로 저장
	        // 1) 사용자가 업로드할 때 사용한 파일명
	        // 2) 실제 서버에 업로드할 때 사용한 경로
	        // 데이터베이스에 이미지 경로 저장 (이미지 경로 수정)
	        minwonVO.setChumbuImage(String.join(":", imageList));
	        
	        // 서비스에서 업데이트 처리
	        minwonService.minwonUpdate(minwonVO);
	     }
	    
	    return imageList;
	}
	
	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath);
		// File newFile= new File(dir,"파일명");
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}

	private String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}


	// 삭제(처리)
	@GetMapping("minwonDelete")
	public String minwonDelete(Integer postNo) {
		minwonService.minwonDelete(postNo);
		return "redirect:minwonList";
	}
		
}
