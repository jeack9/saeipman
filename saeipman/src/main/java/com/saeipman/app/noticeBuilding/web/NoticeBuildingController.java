package com.saeipman.app.noticeBuilding.web;

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

import com.saeipman.app.noticeBuilding.service.NoticeBuildingService;
import com.saeipman.app.noticeBuilding.service.NoticeBuildingVO;
import com.saeipman.app.noticeBuilding.utils.PagingSearchDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 불러오는 어노테이션
@Controller
public class NoticeBuildingController {
	private NoticeBuildingService noticeBuildingService;

	@Value("${file.upload.path}") // 경로(file.upload.path)를 uploadPath 변수에 할당함.
	private String uploadPath;

	@Autowired
	public NoticeBuildingController(NoticeBuildingService noticeBuildingService) {
		this.noticeBuildingService = noticeBuildingService;
	}

	// 전체조회
	@GetMapping("noticeBuildingList")
	public String noticeBuildingList(NoticeBuildingVO noticeBuildingVO, PagingSearchDTO pgsc, Model model) {
		List<NoticeBuildingVO> list = noticeBuildingService.noticeBuildingList(pgsc);
		noticeBuildingService.noticeBuildingViews(noticeBuildingVO);
		model.addAttribute("BNotice", list);
		return "noticeBuilding/noticeBuildingList";
	}

	// 단건조회
	@GetMapping("noticeBuildingInfo")

	public String noticeBuildingInfo(NoticeBuildingVO noticeBuildingVO, Model model) {
		noticeBuildingService.noticeBuildingViews(noticeBuildingVO);
		NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
		model.addAttribute("BNotice", selectVO);
		return "noticeBuilding/noticeBuildingInfo";
	}

	// 등록(페이지)
	@GetMapping("noticeBuildingInsert")
	public String noticeBuildingInsertForm() {
		return "noticeBuilding/noticeBuildingInsert";
	}

	// 등록(처리) + 파일 업로드(처리)
	@PostMapping("noticeBuildingInsert")
	public String noticeBuildingProc(@RequestPart MultipartFile[] files, NoticeBuildingVO noticeBuildingVO) {

		List<String> fileList = new ArrayList<>();

		log.info(uploadPath);
		for (MultipartFile file : files) {
			log.info(file.getContentType());
			log.info(file.getOriginalFilename());
			log.info(String.valueOf(file.getSize()));

			String fileName = file.getOriginalFilename();
			String saveName = uploadPath + File.separator + fileName; // separator => java가 / 경로 인식 못하니까 java가 인식할 수 있는
																		// 경로를 설정해주기 위해 썼음.

			log.debug("saveName : " + saveName);

			Path savePath = Paths.get(saveName); // Path => java내에서 경로 처리하는 객체 즉, 경로 정의하고 파일 정보를 가져오려고 썼음!

			try {
				file.transferTo(savePath);// transferTo : 업로드 작업 진행
				fileList.add(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(fileList);
		noticeBuildingVO.setGroupId(String.join(":", fileList));

		int no = noticeBuildingService.noticeBuildingInsert(noticeBuildingVO);
		return "redirect:noticeBuildingInfo?postNo=" + no;
	}

	// 수정(페이지)
	@GetMapping("noticeBuildingUpdate")
	public String noticeBuildingUpdateForm(NoticeBuildingVO noticeBuildingVO, Model model) {
		NoticeBuildingVO selectVO = noticeBuildingService.noticeBuildingSelect(noticeBuildingVO);
		model.addAttribute("BNotice", selectVO);
		return "noticeBuilding/noticeBuildingUpdate";
	}

	// 수정(처리)
	@PostMapping("noticeBuildingUpdate")
	@ResponseBody
	public List<String> noticeBuildingUpdate(@RequestPart MultipartFile[] files, NoticeBuildingVO noticeBuildingVO) {

		List<String> fileList = new ArrayList<String>();

		for (MultipartFile uploadFile : files) {

			// 이미지 파일 말고 다른거 올릴 수도 있지 않을까 싶어서 일단 주석해놨음...
//				if(uploadFile.getContentType().startsWith("image") == false) {
//					System.err.println("this file is not image type");
//		    		return null;
//		        }

			// 중복 파일 관리
			String fileName = uploadFile.getOriginalFilename();

			System.out.println("fileName : " + fileName);

			// 날짜 폴더 생성
			String folderPath = makeFolder();

			// UUID(UUID는 고유 ID임. 중복 제한하려고 사용함!)
			String uuid = UUID.randomUUID().toString();
			String uploadFileName = folderPath + File.separator + uuid + "_" + fileName;
			String saveName = uploadPath + File.separator + uploadFileName;
			Path savePath = Paths.get(saveName);
			System.out.println("path : " + saveName);

			try {
				uploadFile.transferTo(savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			noticeBuildingVO.setGroupId(String.join(":", fileList));
		}

		return fileList;
	}

	// LocalDate~ => "yyyy/MM/dd" 형식으로 오늘날짜 변환해서 가져오려고 썼음.
	// replace 하는 이유는 "/"를 개발자가 사용하는 운영체제 형식에 맞게(File.separator) 변경하기 위해서임.
	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String folderPath = str.replace("/", File.separator);
		File uploadPathFolder = new File(uploadPath, folderPath);
		// uploadPath(경로)랑 folderPath(날짜)랑 합쳐서 사용하려고.. 예를들어 c:/uploads/2024/09/03

		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
			// mkdirs => 상위 디렉토리 존재하지 않을 겨우 상위까지 모두 생성
		}
		return folderPath;
	}

	private String setFilePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}

	// 삭제(처리)
	@GetMapping("noticeBuildingDelete")
	public String noticeBuildingDelete(Integer no) {
		noticeBuildingService.noticeBuildingDelete(no);
		return "redirect:noticeBuildingList";
	}
	
	//조회수
	
	
	
}
