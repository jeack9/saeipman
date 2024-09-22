package com.saeipman.app.gwanlibi.web;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saeipman.app.building.service.BuildingPageDTO;
import com.saeipman.app.building.service.BuildingService;
import com.saeipman.app.building.service.BuildingVO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.gwanlibi.service.GaguGwanlibiHistoryVO;
import com.saeipman.app.gwanlibi.service.GwanlibiMsgService;
import com.saeipman.app.gwanlibi.service.GwanlibiMsgVO;
import com.saeipman.app.gwanlibi.service.GwanlibiPaymentService;
import com.saeipman.app.gwanlibi.service.GwanlibiPaymentVO;
import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.message.MsgService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor // 롬복 어노테이션 - 모든 필드의 생성자 생성
public class GwanlibiController {

	// 필드
	private GwanlibiService gwanlibiService;
	private BuildingService buildingService;
	private GwanlibiMsgService gwanlibiMsgService;
	private GwanlibiPaymentService gwanlibiPaymentService;
	private MsgService msgService;

	public String getYM() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		return dateFormat.format(date);
	}

	// 전월 구하기
	public String preYM() {
		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String ym = year + "-" + month;
		return ym;
	}

	public String numberToString(double gwanlibi) {
		String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
		return result;
	}

	// 로그인한 사용자(임대인)의 건물들을 목록으로 출력하고, 해당 건물의 전월, 금월 관리비 출력 화면으로 이동
	@GetMapping("buildingGwanlibiList")
	public String gwanlibiListForm(Model model, BuildingPageDTO dto) {
		LoginInfoVO login = SecurityUtil.getLoginInfo();
		// System.out.println(login.getLoginId());

		// 한 페이지당 출력할 건물 개수 - 페이징
		dto.setAmount(6);

		// 출력할 건물의 총 개수
		int total = gwanlibiService.buildingTotalCount(login.getLoginId());
		dto.setTotal(total);

		List<GwanlibiVO> list = gwanlibiService.monthGwanlibiByBuildingList(login.getLoginId(), dto);

		model.addAttribute("list", list);
		model.addAttribute("page", dto);

		return "gwanlibi/buildingGwanlibiList";
	}

	// 관리비 항목 리스트
	@GetMapping("gwanlibiItemList")
	// public String gwanlibiItemList(@RequestParam(name = "buildingId") String
	// buildingId,
	public String gwanlibiItemList(BuildingVO buildingVO, Model model) {

		// 건물 단건 조회 - 건물 이름이 필요해서
		BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);

		// List<GwanlibiVO> list = gwanlibiService.itemList(buildingId);
		List<GwanlibiVO> gwanlibiItemlist = gwanlibiService.itemList(buildingInfo.getBuildingId());
		System.err.println(gwanlibiItemlist.size());

		// 관리비 항목 설정이 안 되어있으면 기본 관리비 항목 출력
		if (gwanlibiItemlist.size() <= 0) {
			gwanlibiItemlist = gwanlibiService.basicGwanlibiList();
			gwanlibiItemlist.forEach(ele -> {
				ele.setVariableYn("변동");
//				ele.setBuildingId(buildingId);
				ele.setBuildingId(buildingInfo.getBuildingId());
			});
		}

		model.addAttribute("gwanlibiItemlist", gwanlibiItemlist);
		model.addAttribute("buildingInfo", buildingInfo);

		return "gwanlibi/gwanlibiItemList";
	}

	// 관리비 항목 등록 - 아작스
	@PostMapping("insertItems")
	@ResponseBody
	public int insertItems(@RequestBody List<GwanlibiVO> list, @RequestParam(name = "buildingId") String buildingId) {

		int cnt = 0;
		System.err.println(buildingId + "건물번호");
		// 관리비 항목 최대 버전 + 1 가져오기.
		int version = gwanlibiService.getUpdateVesion(buildingId);

		// 버전 값 넣어주기
		for (GwanlibiVO item : list) {
			item.setVersion(version);
			item.setBuildingId(buildingId);
			cnt++;
		}

		// 관리비 항목
		gwanlibiService.addtItems(list);

		return cnt;
	}

	// 건물별 관리비 상세 내역 출력 화면으로 이동
	@GetMapping("gwanlibiDetailsBillList")
	public String detailsBillList(Model model, GwanlibiVO gwanlibiVO, BuildingVO buildingVO) {

		// 관리비 상세 내역 목록
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(gwanlibiVO);
		System.err.println(detailsList);

		// 빌딩 정보 가져오기. - 단건 조회
		BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);

		// 가구별 관리비 number format 설정.
		for (GwanlibiVO list : detailsList) {
			list.setStrGwanlibiByGagu(numberToString(list.getGwanlibiByGagu()));
			list.setStrGwanlibiItemMoney(numberToString(list.getGwanlibiItemMoney()));
		}

		model.addAttribute("detailsList", detailsList);
		model.addAttribute("buildingInfo", buildingInfo);
		return "gwanlibi/gwanlibiDetailsBillList";
	}

	// 월별 건물별 관리비 상세 내역 출력 화면으로 이동- ajax
	@PostMapping("gwanlibiDetailsBillList")
	@ResponseBody
	public Map<String, Object> reDetailsBillList(GwanlibiVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(vo);

		// 가구별 관리비, 관리 비용 number format 설정.
		for (GwanlibiVO list : detailsList) {
			list.setStrGwanlibiByGagu(numberToString(list.getGwanlibiByGagu()));
			list.setStrGwanlibiItemMoney(numberToString(list.getGwanlibiItemMoney()));
		}

		map.put("detailsList", detailsList);
		return map;
	}

	// 건물별 관리비 정산 화면 선택
	@PostMapping("gwanlibiCalculationForm")
	@ResponseBody
	public Map<String, Object> insertGwanlibi(@RequestParam(name = "buildingId") String buildingId,
			GwanlibiVO gwanlibiVO, BuildingVO buildingVO, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 예외 처리
		List<GaguGwanlibiHistoryVO> roomList = gwanlibiService.roomIdList(buildingId);
		int roomListSize = roomList.size();
		map.put("roomListSize", roomListSize);

		// 설정된 관리비 항목 리스트의 데이터 개수
		List<GwanlibiVO> gwanlibiItmeList = gwanlibiService.itemList(buildingId);
		int gwanlibiItmeListSize = gwanlibiItmeList.size();
		map.put("gwanlibiItmeListSize", gwanlibiItmeListSize);

		// 이미 관리비 정산을 했으면 수정 화면으로, 아니면 등록 화면으로.
		int dataCnt = gwanlibiService.getCountingMonthGwanlibiData(buildingId);
		if (dataCnt <= 0) {
			String url = "/insertGwanlibi?buildingId=" + buildingId;
			map.put("url", url);
		} else {
			// paymentMont -> 전월
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			// cal.add(Calendar.MONTH, -1);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			String paymentMonth = year + "-" + month;

			String url = "/updateGwanlibi?buildingId=" + buildingId + "&paymentMonth=" + paymentMonth;
			map.put("url", url);
		}
		return map;
	}

	// 관리비 등록 화면
	@GetMapping("insertGwanlibi")
	public String insertGwanlibiForm(@RequestParam(name = "buildingId", required = false) String buildingId,
			GwanlibiVO gwanlibiVO, BuildingVO buildingVO, Model model) {

		List<GwanlibiVO> gwanlibiItemList = gwanlibiService.itemList(buildingId);
		BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);
		model.addAttribute("gwanlibiItemList", gwanlibiItemList);
		model.addAttribute("buildingInfo", buildingInfo);
		return "gwanlibi/insertGwanlibi";
	}

	// 관리비 등록 처리 - 아작스
	@PostMapping("insertGwanlibi")
	@ResponseBody
	public String insertGwanlibi(@RequestParam(name = "paymentDate") String paymentDate,
			@RequestBody List<GwanlibiVO> gridDatalist) throws ParseException {

		String buildingId = gridDatalist.get(0).getBuildingId();
		// 방 리스트
		List<GaguGwanlibiHistoryVO> roomIdList = gwanlibiService.roomIdList(buildingId);

		String ym = preYM();
		String date = ym + "-" + paymentDate;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date paymentMonth = formatter.parse(date);
		System.err.println(paymentDate + "/" + date);
		System.err.println("결과 = " + paymentMonth);
		gridDatalist.get(0).setPaymentMonth(paymentMonth);

		gwanlibiService.addGwanlibi(gridDatalist, roomIdList);

		return "/gwanlibiDetailsBillList?buildingId=" + buildingId + "&paymentMonth=" + ym;
	}

	// 관리비 업데이트 화면
	@GetMapping("updateGwanlibi")
	public String updateGwanlibiForm(Model model, GwanlibiVO gwanlibiVO, BuildingVO buildingVO) {
		// 관리비 상세 내역 목록
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(gwanlibiVO);
		System.err.println(detailsList);

		// 빌딩 정보 가져오기. - 단건 조회
		BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);

		model.addAttribute("detailsList", detailsList);
		model.addAttribute("buildingInfo", buildingInfo);

		return "gwanlibi/updateGwanlibi";
	}

	// 관리비 업데이트 처리
	@PostMapping("updateGwanlibi")
	@ResponseBody
	public void updateGwanlibiProcess(@RequestBody List<GwanlibiVO> gridDatalist) {
		gwanlibiService.modifyGwanlibi(gridDatalist);
	}

	// 문자 /sendSMSMsg 요청 -> sendSMSMessage()
	@GetMapping("sendSMSMsg")
	@ResponseBody
	public String sendSMSMessage(@RequestParam(name = "buildingId") String buildingId,
			@RequestParam(name = "total") String total, @RequestParam(name = "paymentDate") String paymentDate) {
		// 해당 건물에 거주하고 있는 임차인의 연락처 조회.
		List<GwanlibiMsgVO> imdeainList = gwanlibiMsgService.getImchainPhoneNumber(buildingId);

		// 현재 로그인한 임대인 ID 조회 -> 임대인의 연락처.
		LoginInfoVO loginInfoVO = SecurityUtil.getLoginInfo();

		// 임대인 휴대 전화 번호.
		String imdaeinPhoneNumber = gwanlibiMsgService.getImdaeinPhoneNumber(loginInfoVO.getLoginId());

		// 전월.
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		// 메시지 내용.
		String msg = year + "년 " + month + "월 관리비는 " + total + "원입니다.\n납부 기한은 " + paymentDate + "까지입니다.";

		msgService.sendGroup(imdeainList, imdaeinPhoneNumber, msg);

		// console 확인 용도.
		return msg;
	}

	// TODO
	@GetMapping("gwanlibiPaymentState")
	public String gwanlibiPaymentStateList(BuildingVO buildingVO, BuildingPageDTO pageDTO, Model model) {

		LoginInfoVO login = SecurityUtil.getLoginInfo();

		// 한 페이지당 출력할 건물 개수 - 페이징
		pageDTO.setAmount(5);

		// 출력할 건물의 총 개수
		int total = gwanlibiService.buildingTotalCount(login.getLoginId());
		pageDTO.setTotal(total);

		List<BuildingVO> buildingList = buildingService.buildingList(pageDTO, login.getLoginId());
		
		// add roomList
		for (BuildingVO building : buildingList) {			
			List<GwanlibiPaymentVO> rooms = gwanlibiPaymentService.getGwanlibiPaymentStateList(building.getBuildingId());
			model.addAttribute("rooms", rooms);
		}
		
		System.err.println(buildingList);
		model.addAttribute("buildingList", buildingList);
		model.addAttribute("page", pageDTO);

		return "gwanlibi/gwanlibiPaymentState";
	}

}