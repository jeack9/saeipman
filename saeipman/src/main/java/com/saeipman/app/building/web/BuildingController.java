package com.saeipman.app.building.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.file.service.FileService;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.ocrTest.config.OcrApi;
import com.saeipman.app.ocrTest.config.OcrUtil;
import com.saeipman.app.room.service.RoomVO;
import com.saeipman.app.upload.config.FileUtility;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BuildingController {

	@Value("${naver.service.secretKey}")
	private String secretKey;

	private final OcrApi naverApi;
	private final OcrUtil ocrUtil;

	private final BuildingService buildingService;
	private final FileUtility fileUtill;
	private final FileService fileService;

	@GetMapping("/buildingList")
	public String buildingInfo(BuildingPageDTO buildingPageDTO, Model model) {
		LoginInfoVO login = SecurityUtil.getLoginInfo();
		model.addAttribute("imdaeinId", login);
		String loginId = SecurityUtil.getLoginId();

		// 리스트 총 수
		int total = buildingService.totalPage(login.getLoginId());
		buildingPageDTO.setTotal(total);

		// 리스트 출력
		List<BuildingVO> list = buildingService.buildingList(buildingPageDTO, login.getLoginId());

		model.addAttribute("buildings", list);
		model.addAttribute("page", buildingPageDTO);

		return "building/buildingList";
	}

	@GetMapping("/buildingDetails")
	@ResponseBody
	public BuildingVO buildingDetails(@RequestParam("id") String buildingId, Model model) {
		BuildingVO buildingVO = new BuildingVO();
		RoomVO roomVO = new RoomVO();

		buildingVO.setBuildingId(buildingId);
		BuildingVO result = buildingService.buildingInfo(buildingVO);
		if (result == null) {
			return null;
		}
		// 방 정보
		roomVO.setBuildingId(buildingId);

		List<RoomVO> roomSelect = buildingService.roomSelect(roomVO);
		System.out.println(roomSelect + "확인");
		result.setRooms(roomSelect);

		// 파일 정보
		List<String> fileName = fileService.getFileName(buildingVO.getBuildingId());
		System.out.println("파일" + fileName);

		result.setFileName(fileName);

		return result;
	}

	@GetMapping("/buildingInsert")
	public String insertBuildingForm() {
		return "building/buildingInsert";
	}

	@PostMapping("/buildingInsert")
	@ResponseBody
	public String insertBuilding(@RequestPart(name = "files") MultipartFile[] files,
			@RequestPart(name = "ocrFile") MultipartFile ocrFile, BuildingVO buildingVO, RoomVO roomVO)
			throws IOException {
		System.out.println(files +"파일명");
		// 업로드 경로 폴더명
		fileUtill.setFolder("건물");

		LoginInfoVO login = SecurityUtil.getLoginInfo();

		String groupId = fileUtill.multiUpload(files, "-1");
		String ocr = fileUtill.singleUpload(ocrFile);

		buildingVO.setImdaeinId(login.getLoginId());
		buildingVO.setGroupId(groupId);
		buildingVO.setOcrFileName(ocr);
		buildingService.buildingInsert(buildingVO);

		return buildingVO.getBuildingId();
	}

	@PostMapping("/selectRoomInsert")
	@ResponseBody
	public Map<String, Object> selectRoomInsert(@RequestBody List<RoomVO> list) {

		Map<String, Object> result = buildingService.roomSelectInsert(list);
		return result;
	}

	@PostMapping("/ocrUpload")
	@ResponseBody
	public Map<String, Object> insertOcr(@RequestPart(name = "ocrFile") MultipartFile ocrFile, Model model,
			BuildingVO buildingVO) throws IOException {
//		if (ocrFile.isEmpty()) {
//			return "error"; // 파일이 비어있을 경우 에러를 처리하는 HTML 템플릿으로 이동
//		}

		String naverSecretKey = secretKey; // 본인의 네이버 Clova OCR 시크릿 키로 대체

		File tempFile = File.createTempFile("temp", ocrFile.getOriginalFilename());
		ocrFile.transferTo(tempFile);

		List<String> result = naverApi.callApi("POST", tempFile.getPath(), naverSecretKey, "jpg");

		tempFile.delete(); // 임시 파일 삭제
		Map<String, Object> map = new HashMap<String, Object>();

		// String merged = ocrUtil.mergeWords(result);
		String fullAddress = ocrUtil.mergeWords(result, "도로명주소").split("대지면적")[0];
		String gunchookArea = ocrUtil.mergeWords(result, "건축면적").split("※건폐율")[0];
		String saedaeArea = ocrUtil.mergeWords(result, "호수/가구수/세대수").split("대지위치")[0];
		String heighArea = ocrUtil.mergeWords(result, "높이").split("※조경면적")[0];
		String floorArea = ocrUtil.mergeWords(result, "연면적").split("건축면적")[0];

		// int lastAddr = fullAddress.indexOf(fullAddress);
		int firstM2 = gunchookArea.indexOf("m2");
		int lastM2 = gunchookArea.lastIndexOf("m2");
		int lastFloor = gunchookArea.lastIndexOf("층");
		int lastSaedae = saedaeArea.lastIndexOf("세대");
		int lastHeigh = heighArea.lastIndexOf("m");
		int lastFloorArea = floorArea.lastIndexOf("m");

		// String address = fullAddress.substring(0,lastFloorArea);
		String gunchook = gunchookArea.substring(0, firstM2);
		String floorAreaRatio = gunchookArea.substring(0, lastM2);
		String floor = gunchookArea.substring(0, lastFloor);
		String saedae = saedaeArea.substring(0, lastSaedae);
		String heigh = heighArea.substring(0, lastHeigh);
		String fArea = floorArea.substring(0, lastFloorArea);

		int addrLastPos = fullAddress.lastIndexOf(" ");
		int gunchookLastPos = gunchook.lastIndexOf(" ");
		int floorAreaRatioLastPos = floorAreaRatio.lastIndexOf(" ");
		int floorLastPos = floor.lastIndexOf(":") + 1;
		// System.out.println(saedae);
		// int saedaeLastPos = saedae.lastIndexOf(" ");
		int heighLastPos = heigh.lastIndexOf(" ");
		int fAreaLastPos = fArea.lastIndexOf(" ");

		fullAddress = fullAddress.substring(0, addrLastPos).trim();
		gunchook = gunchookArea.substring(gunchookLastPos, firstM2).trim();
		floorAreaRatio = gunchookArea.substring(floorAreaRatioLastPos, lastM2).trim();
		floor = gunchookArea.substring(floorLastPos, lastFloor).trim();
		// saedae = saedaeArea.substring(saedaeLastPos, lastSaedae).trim();
		heigh = heighArea.substring(heighLastPos, lastHeigh).trim();
		fArea = floorArea.substring(fAreaLastPos, lastFloorArea).trim();
		String ocrFileName = ocrFile.getOriginalFilename();
		map.put("ocrFileName", ocrFileName);
		map.put("fullAddress", fullAddress);
		map.put("gunchook", gunchook);
		map.put("floorAreaRatio", floorAreaRatio);
		map.put("floor", floor);
		map.put("saedae", saedae);
		map.put("heigh", heigh);
		map.put("floorArea", fArea);

		return map;
	}

	@PostMapping("/buildingUpdate")
	@ResponseBody
	public Map<String, Object> updateBuilding(BuildingVO buildingVO, RoomVO roomVO,
			@RequestPart(name = "newFiles", required = false) MultipartFile[] newFiles,
			@RequestParam(name = "deleteFileNames", required = false) List<String> deleteFileNames,
			@RequestParam(name = "updateRoomList", required = false) List<String> updateRoomList,
			@RequestParam(name = "deleteRoomList", required = false) List<String> deleteRoomList) {
		 System.out.println("새파일" + newFiles);
		
		fileUtill.setFolder("건물");
		// 방번호 수정
		if(updateRoomList!=null) {
			
			for (String updateRoom : updateRoomList) {
				// json 문자열을 파싱
				JSONParser parser = new JSONParser();
				try {
					JSONObject updateListArr = (JSONObject) parser.parse(updateRoom); // JSON 배열로 변환
					// JSONArray에서 RoomVO 객체로 변환하거나 원하는 방식으로 데이터를 사용
					String roomId = (String) updateListArr.get("roomId");
					String roomString = (String) updateListArr.get("roomNo");
					Integer roomNo = Integer.valueOf(roomString);
					// (roomId와 roomNo를 RoomVO에 매핑 등)
					// System.out.println(roomId+"룸Id");
					// System.out.println(roomNo+"룸No");
					roomVO.setRoomId(roomId);
					roomVO.setRoomNo(roomNo);
					buildingService.roomUpdate(roomVO);
					
				} catch (ParseException e) {
					e.printStackTrace(); // 예외 처리
				}
				
			}
		}
		// 방번호 삭제
		if(deleteRoomList!=null) {
			
			for (String deleteRoom : deleteRoomList) {
				// json 문자열을 파싱
				JSONParser parser = new JSONParser();
				try {
					JSONObject deleteListArr = (JSONObject) parser.parse(deleteRoom); // JSON 배열로 변환
					// JSONArray에서 RoomVO 객체로 변환하거나 원하는 방식으로 데이터를 사용
					String roomId = (String) deleteListArr.get("roomId");
					roomVO.setRoomId(roomId);
					buildingService.roomInfoDelete(roomVO);
					
				} catch (ParseException e) {
					e.printStackTrace(); // 예외 처리
				}
				
			}
		}
		// 파일 삭제 처리
		if (deleteFileNames != null && !deleteFileNames.isEmpty()) {
			System.out.println("여길 삭제");
			buildingService.fileDelete(deleteFileNames);
			System.out.println(deleteFileNames + "삭제 파일 확인1");
			for (

			String fileName : deleteFileNames) {
				fileUtill.deleteFile(fileName); // 실제 파일 삭제
				// buildingService.fileDelete(fileName);
			}
		}

		// 새 파일 업로드 처리
		if(newFiles!= null && newFiles.length > 0) {
			System.out.println("여길 수정");
			String groupId = buildingVO.getGroupId(); // 기존 그룹 ID 가져오기
			
			// group_id가 없으면 새로 생성
			groupId = fileUtill.multiUpload(newFiles, groupId);
			buildingVO.setGroupId(groupId);
			
			return buildingService.buildingUpdate(buildingVO);
		}

		// 방정보 수정
		
		return buildingService.buildingUpdate(buildingVO);
	}

	@GetMapping("/buildingDelete") // 관리비가 존재한다면 알림
	public String buildingDelete(@RequestParam("id") String buildingId) {
		buildingService.roomDelete(buildingId);
		buildingService.buildingDelete(buildingId);
		return "redirect:buildingList";
	}

}
