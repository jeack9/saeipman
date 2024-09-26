package com.saeipman.app.room.web;

import java.io.IOException;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.member.service.MemberService;
import com.saeipman.app.room.service.BuildingRoom;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RentPayService;
import com.saeipman.app.room.service.RoomService;
import com.saeipman.app.room.service.RoomVO;
import com.saeipman.app.upload.config.FileUtility;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {
	private final RoomService rsvc;
	private final ConstractService csvc;
	private final BuildingService bsvc;
	private final MemberService msvc;
	private final RentPayService rentPaySvc;
	private final FileUtility fileUtill;

	// 방목록 페이지이동
	@GetMapping("roomListBackup")
	public void roomP(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "buildingId", required = false) String buildingId, Model model) {
		String imdaeinId = SecurityUtil.getLoginId();
		// 건물 선택
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingId = buildingId == null ? "" : buildingId;
		buildingRoom.setImdaeinId(imdaeinId);
		buildingRoom.setBuildingId(buildingId);
		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBuildingId(buildingId);
		BuildingVO findBuildingVO = bsvc.buildingInfo(buildingVO);
		if (findBuildingVO == null) {
			findBuildingVO = new BuildingVO();
		}
		model.addAttribute("buildingVO", findBuildingVO);

		// 임대인의 건물리스트 모달창용 페이지네이션
		int buildingPage = 1; // 수정해야함 값 받아오도록
		int buildingTotal = bsvc.totalPage(imdaeinId);
		PagingDTO buildingPaging = new PagingDTO(buildingPage, 4, buildingTotal, 5);
		List<BuildingVO> buildingList = bsvc.imdaeinBuildingList(buildingPaging, imdaeinId);
		model.addAttribute("buildingList", buildingList);
		model.addAttribute("bPaging", buildingPaging);

		// 방 목록 페이지네이션
		page = page == null ? 1 : page;
		int total = rsvc.totalBuildingRoom(buildingRoom);
		PagingDTO paging = new PagingDTO(page, 6, total, 5);

		// 방 목록 검색조건

		// 건물 - 거주 공실 수
		Map<String, Object> map = new HashMap<String, Object>();
		int ipju = rsvc.buildingIpjuCount(buildingRoom);
		map.put("total", total);
		map.put("ipju", ipju);
		model.addAttribute("hosu", map);

		List<BuildingRoom> roomList = rsvc.buildingRoomList(buildingRoom, paging);
		model.addAttribute("roomList", roomList);
		model.addAttribute("paging", paging);
	};

	// 현재 계약정보 htmlFrg 반환 ajax
	@PostMapping("getConstract")
	public String getConstract(@RequestBody HashMap<String, Object> map, Model model) {
		ConstractVO findVo = csvc.constractInfo((String) map.get("constractNo"));
		// 현재 계약정보 없으면 다음 계약정보 조회
		if (findVo == null) {
			findVo = csvc.nextConstractInfoByRoomId((String) map.get("roomId"));
		}
		// 다음 계약정보 없으면 빈 정보 전달
		if (findVo == null) {
			findVo = new ConstractVO();
		}
		RoomVO roomVo = rsvc.roomInfo((String) map.get("roomId"));

		model.addAttribute("constractVo", findVo);
		model.addAttribute("roomVo", roomVo);
		model.addAttribute("buildingName", (String) map.get("buildingName"));
		model.addAttribute("page", (String) map.get("page"));
		model.addAttribute("buildingId", (String) map.get("buildingId"));
		return "room/fragments/constractModal :: modalContent";
	}

	// 다음 계약정보 모달창 반환
	@PostMapping("getNextConstract")
	public String getNextConstract(@RequestBody HashMap<String, Object> map, Model model) {
		ConstractVO findVo = csvc.nextConstractInfoByRoomId((String) map.get("roomId"));
		RoomVO roomVo = rsvc.roomInfo((String) map.get("roomId"));
		if (findVo == null) {
			findVo = new ConstractVO();
		}
		model.addAttribute("constractVo", findVo);
		model.addAttribute("roomVo", roomVo);
		model.addAttribute("buildingName", (String) map.get("buildingName"));
		model.addAttribute("page", (String) map.get("page"));
		model.addAttribute("buildingId", (String) map.get("buildingId"));
		return "room/fragments/constractModal :: modalContent";
	}

	// 현재사용안함
	@PostMapping("loadRoomListFrg/{buildingId}")
	public String loadRoomListFrg(@RequestParam(name = "page", required = false) Integer page,
			@PathVariable String buildingId, Model model) {
		// 건물 선택
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingRoom.setImdaeinId(SecurityUtil.getLoginId());
		buildingRoom.setBuildingId(buildingId);

		// 페이지네이션
		page = page == null ? 1 : page;
		int total = rsvc.totalBuildingRoom(buildingRoom);
		PagingDTO paging = new PagingDTO(page, 4, total, 5);

		// 검색조건

		// 건물 - 거주 공실 수
		Map<String, Object> map = new HashMap<String, Object>();
		int ipju = rsvc.buildingIpjuCount(buildingRoom);
		map.put("total", total);
		map.put("ipju", ipju);
		model.addAttribute("hosu", map);

		List<BuildingRoom> roomList = rsvc.buildingRoomList(buildingRoom, paging);
		model.addAttribute("roomList", roomList);
		model.addAttribute("paging", paging);
		return "room/fragments/roomWrapFrg :: roomWrap";
	}

	// 건물목록Frg모달창 ajax 반환
	@GetMapping("buildingList")
	public String buildingList(@RequestParam(name = "buildingPage", required = false) Integer buildingPage,
			Model model) {
		String imdaeinId = SecurityUtil.getLoginId();
		// 임대인의 건물리스트 모달창용
		buildingPage = buildingPage == null ? 1 : buildingPage; // 수정해야함 값 받아오도록
		int buildingTotal = bsvc.totalPage(imdaeinId);
		PagingDTO buildingPaging = new PagingDTO(buildingPage, 4, buildingTotal, 5);
		List<BuildingVO> buildingList = bsvc.imdaeinBuildingList(buildingPaging, imdaeinId);
		model.addAttribute("buildingList", buildingList);
		model.addAttribute("bPaging", buildingPaging);

		return "room/fragments/buildingList :: buildingListFrg";
	}

	// 방 계약정보 등록 multipart
	@PostMapping("/insertConstract")
	@ResponseBody
	public Map<String, Object> insertConstract(@Valid ConstractVO constractVO, BindingResult bindingResult,
			@RequestParam(name = "file") MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		// 업로드 경로 폴더명
		fileUtill.setFolder("계약서");
		// 유효성 검사
		if (bindingResult.hasErrors()) {
			map.put("retCode", "fail3");
			return map;
		}
		
		String roomId = constractVO.getRoomId();
		int newState = constractVO.getConstractState();
		if (newState == -1) {
			map.put("retCode", "fail5");
			return map;
		}

		// 파일 저장 로직
		if (file != null && !file.isEmpty()) {
			if (!file.getContentType().equals("application/pdf")) {
				map.put("retCode", "fail_file_type");
				return map;
			}
			try {
				// 파일을 저장하고 경로를 반환
				String filePath = fileUtill.singleUpload(file);
				constractVO.setConstractFile(filePath); // 파일 경로를 VO에 저장
			} catch (Exception e) {
				e.printStackTrace();
				map.put("retCode", "fail_file");
				return map;
			}
		} else {
			System.out.println("파일이 비어있습니다.");
		}

		// 대기계약 정보 확인
		if (newState == 0) {
			ConstractVO nextConstract = csvc.nextConstractInfoByRoomId(roomId);
			if (nextConstract != null && !nextConstract.getConstractNo().equals(constractVO.getConstractNo())) {
				map.put("retCode", "fail1");
				System.err.println("인서트: 대기계약 있음.");
				return map;
			}
		}

		// 현재계약 정보 확인
		if (newState == 1) {
			// 계약 확정 등록시 임차인 연락처로 확정된 계약조회
			if(csvc.existsByPhoneActive(constractVO.getImchainPhone())) {
				map.put("retCode", "failExist");
				return map;
			}
			ConstractVO currentConstract = csvc.currentConstractInfoByRoomId(roomId);
			if (currentConstract != null && !currentConstract.getConstractNo().equals(constractVO.getConstractNo())) {
				map.put("retCode", "fail2");
				System.err.println("인서트: 현재계약 있음.");
				return map;
			}
		}

		// 계약 정보 insert 후 방의 입주상태 변경
		String newConstractNo = csvc.addConstract(constractVO);

		// 계약 확정 시 임차인 정보 및 로그인 정보 단건 등록, 월세 첫 납부내역 등록
		if (newState == 1) {
			// 임차인 정보 추가
			msvc.addImchain(constractVO);
			// 월세 첫 납부내역 등록 (납부상태 1)
			rentPaySvc.addRentPayAfterConstract(constractVO);
		}

		// 성공적으로 저장되었을 경우
		map.put("retCode", "ok");
		map.put("newConstractNo", newConstractNo);
		return map;
	}

	// 방 계약정보 수정 multipart
	@PostMapping("/updateConstract")
	@ResponseBody
	public Map<String, Object> updateConstract(@Valid ConstractVO constractVO, BindingResult bindingResult,
			@RequestParam(name = "file", required = false) MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		fileUtill.setFolder("계약서");
		// 유효성 검사
		if (bindingResult.hasErrors()) {
			// 첫 번째 유효성 검사 오류만 추출
			FieldError firstError = bindingResult.getFieldError(); // 가장 첫 번째 오류 추출
			String errorMessage = firstError.getField() + ": " + firstError.getDefaultMessage();

			// 오류 메시지를 클라이언트로 반환
			map.put("retCode", "fail3");
			map.put("error", errorMessage); // 단일 오류 메시지 반환
			return map;
		}

		String roomId = constractVO.getRoomId();
		int newState = constractVO.getConstractState();

		if (file != null && !file.isEmpty()) {
			if (!file.getContentType().equals("application/pdf")) {
				map.put("retCode", "fail_file_type");
				map.put("error", "PDF 파일만 업로드 가능합니다.");
				return map;
			}
			// 새 파일이 업로드되면 기존 파일 삭제 후 새 파일 저장
			try {
				if (constractVO.getConstractFile() != null) {
					fileUtill.deleteFile(constractVO.getConstractFile());
				}
				String filePath = fileUtill.singleUpload(file);
				constractVO.setConstractFile(filePath); // 새 파일 경로 설정
			} catch (Exception e) {
				e.printStackTrace();
				map.put("retCode", "fail_file");
				return map;
			}
		} else {
			// 파일이 비어있으면 기존 파일 경로 유지
			constractVO.setConstractFile(constractVO.getConstractFile());
		}

		// 대기계약 정보 확인
		if (newState == 0) {
			ConstractVO nextConstract = csvc.nextConstractInfoByRoomId(roomId);
			if (nextConstract != null && !nextConstract.getConstractNo().equals(constractVO.getConstractNo())) {
				map.put("retCode", "fail1");
				System.err.println("업데이트: 대기계약 있음.");
				return map;
			}
		}

		// 현재계약 정보 확인
		if (newState == 1) {
			if(csvc.existsByPhoneActive(constractVO.getImchainPhone())) {
				map.put("retCode", "failExist");
				return map;
			}
			ConstractVO currentConstract = csvc.currentConstractInfoByRoomId(roomId);
			if (currentConstract != null && !currentConstract.getConstractNo().equals(constractVO.getConstractNo())) {
				map.put("retCode", "fail2");
				System.err.println("업데이트: 현재계약 있음.");
				return map;
			}
		}

		// 계약정보 update -- 방의 입주상태 변경
		map.put("modiConstract", csvc.modiConstract(constractVO));

		// 계약확정 -> 계약정보를 이용하여 임차인 정보, 로그인 정보 단건등록
		// 이미 등록된 임차인 정보가 있으면 삭제 후 등록
		if (newState == 1) {
			msvc.addImchain(constractVO);
			// 월세 첫 납부내역 등록 (납부상태 1)
			rentPaySvc.addRentPayAfterConstract(constractVO);
		} else {
			// 계약만료 or 대기 -> 임차인 정보 삭제
			msvc.removeImchain(constractVO.getImchainPhone());
			msvc.removeLogin(constractVO.getImchainPhone());
		}
		// 성공적으로 저장되었을 경우
		map.put("retCode", "ok");
		return map;
	}

	// 방 계약정보 등록
//	@PostMapping("insertConstract")
//	@ResponseBody
//	public Map<String, Object> insertConstract(@Valid @RequestBody ConstractVO constractVO, BindingResult bindingResult,
//			Model model) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (bindingResult.hasErrors()) {
//			map.put("retCode", "fail3");
//			return map; // 유효성 오류 발생 시 다시 폼 페이지로 이동
//		}
//		String roomId = constractVO.getRoomId();
//		int newState = constractVO.getConstractState();
//		// 대기계약 정보 확인
//		if (newState == 0) {
//			ConstractVO nextConstract = csvc.nextConstractInfoByRoomId(roomId);
//			if (nextConstract != null && !nextConstract.getConstractNo().equals(constractVO.getConstractNo())) {
//				map.put("retCode", "fail1");
//				System.err.println("인서트: 대기계약 있음.");
//				return map;
//			}
//		}
//		// 현재계약 정보 확인
//		if (newState == 1) {
//			ConstractVO currentConstract = csvc.currentConstractInfoByRoomId(roomId);
//			if (currentConstract != null && !currentConstract.getConstractNo().equals(constractVO.getConstractNo())) {
//				map.put("retCode", "fail2");
//				System.err.println("인서트: 현재계약 있음.");
//				return map;
//			}
//		}
//		// 계약정보 insert -- 방의 입주상태 변경
//		String newConstractNo = csvc.addConstract(constractVO);
//		// 계약확정 -> 계약정보를 이용하여 임차인 정보, 로그인 정보 단건등록
//		// 이미 등록된 임차인 정보가 있으면 삭제 후 등록
//		if (newState == 1) {
//			msvc.addImchain(constractVO);
//			// 월세 첫 납부내역 등록 (납부상태 1)
//			rentPaySvc.addRentPayAfterConstract(constractVO);
//		}
//		map.put("retCode", "ok");
//		map.put("newConstractNo", newConstractNo);
//		return map;
//	}

	// 방 계약정보 수정
//	@PostMapping("updateConstract")
//	@ResponseBody
//	public Map<String, Object> updateConstract(@Valid @RequestBody ConstractVO constractVO, BindingResult bindingResult,
//			Model model) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (bindingResult.hasErrors()) {
//			map.put("retCode", "fail3");
//			return map; // 유효성 오류 발생 시 다시 폼 페이지로 이동
//		}
//		String roomId = constractVO.getRoomId();
//		int newState = constractVO.getConstractState();
//		// 대기계약 정보 확인
//		if (newState == 0) {
//			ConstractVO nextConstract = csvc.nextConstractInfoByRoomId(roomId);
//			if (nextConstract != null && !nextConstract.getConstractNo().equals(constractVO.getConstractNo())) {
//				map.put("retCode", "fail1");
//				System.err.println("업데이트: 대기계약 있음.");
//				return map;
//			}
//		}
//		// 현재계약 정보 확인
//		if (newState == 1) {
//			ConstractVO currentConstract = csvc.currentConstractInfoByRoomId(roomId);
//			if (currentConstract != null && !currentConstract.getConstractNo().equals(constractVO.getConstractNo())) {
//				map.put("retCode", "fail2");
//				System.err.println("엄데이트: 현재계약 있음");
//				return map;
//			}
//		}
//		// 계약정보 update -- 방의 입주상태 변경
//		map.put("modiConstract", csvc.modiConstract(constractVO));
//		// 계약확정 -> 계약정보를 이용하여 임차인 정보, 로그인 정보 단건등록
//		// 이미 등록된 임차인 정보가 있으면 삭제 후 등록
//		if (newState == 1) {
//			msvc.addImchain(constractVO);
//			msvc.addImchain(constractVO);
//			// 월세 첫 납부내역 등록 (납부상태 1)
//			rentPaySvc.addRentPayAfterConstract(constractVO);
//		} else {
//			// 계약만료 or 대기 -> 임차인 정보 삭제
//			msvc.removeImchain(constractVO.getImchainPhone());
//			msvc.removeLogin(constractVO.getImchainPhone());
//		}
//		map.put("retCode", "ok");
//		return map;
//	}

	// 계약관리 페이지 이동
	@GetMapping("constractList")
	public void constractListP(@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "buildingId", defaultValue = "", required = false) String buildingId, Model model) {
		String imdaeinId = SecurityUtil.getLoginId();

		// 임대인의 건물리스트 모달창용 페이지네이션
		int buildingPage = 1; // 수정해야함 값 받아오도록
		int buildingTotal = bsvc.totalPage(imdaeinId);
		PagingDTO buildingPaging = new PagingDTO(buildingPage, 4, buildingTotal, 5);
		List<BuildingVO> buildingList = bsvc.imdaeinBuildingList(buildingPaging, imdaeinId);
		model.addAttribute("buildingList", buildingList);
		model.addAttribute("bPaging", buildingPaging);

		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setBuildingId(buildingId);
		BuildingVO findBuildingVO = bsvc.buildingInfo(buildingVO);
		findBuildingVO = findBuildingVO == null ? new BuildingVO() : findBuildingVO;
		model.addAttribute("buildingVO", findBuildingVO);

		// 계약 목록 페이지네이션
		page = page == null ? 1 : page;
		int total = csvc.roomConstractTotal(buildingId);
		PagingDTO paging = new PagingDTO(page, 10, total, 10);
		model.addAttribute("paging", paging);

		// 건물선택 - 방계약 목록
		List<Map<String, Object>> constractList = csvc.roomConstractList(buildingId, paging);
		model.addAttribute("constractList", constractList);

		// 방 계약내역 첫 세팅(null 오류 방지용)
		RoomVO roomVO = new RoomVO();
		model.addAttribute("roomVO", roomVO);
		PagingDTO cPaging = new PagingDTO(1, 10, 0, 10);
		model.addAttribute("cPaging", cPaging);

	}

	// 방의 계약내역 모달창Frg
	@GetMapping("getConstrastHistorys")
	public String getConstrastHistorys(@RequestParam(name = "roomId") String roomId,
			@RequestParam(name = "page", required = false) Integer page, Model model) {
		// 계약 목록 페이지네이션
		page = page == null ? 1 : page;
		int total = rsvc.roomConstractTotal(roomId);
		System.out.println("total" + total);
		PagingDTO cPaging = new PagingDTO(page, 10, total, 10);
		model.addAttribute("cPaging", cPaging);

		// 계약목록
		List<ConstractVO> constracts = rsvc.roomConstractsPaging(roomId, cPaging);
		model.addAttribute("constracts", constracts);

		// 방 정보
		RoomVO roomVO = rsvc.roomInfo(roomId);
		model.addAttribute("roomVO", roomVO);

		return "room/fragments/constractHistory :: constractHistoryFrg";
	}
	
	// 이전 계약정보 조회
	@GetMapping("/prevConstract")
	@ResponseBody
	public Map<String, Object> getPrevConstract(@RequestParam(name = "roomId") String roomId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", false);
		ConstractVO prevConstract = csvc.prevConstractByRoomId(roomId);
		if(prevConstract != null) {
			map.put("retCode", true);
			map.put("prevConstract", prevConstract);
			map.put("expDate", prevConstract.getExpDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		return map;
	}
	
}
