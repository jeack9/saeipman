package com.saeipman.app.gwanlibi.web;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.saeipman.app.gwanlibi.service.GwanlibiService;
import com.saeipman.app.gwanlibi.service.GwanlibiVO;
import com.saeipman.app.member.service.LoginInfoVO;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class GwanlibiController {

	private GwanlibiService gwanlibiService;
	private BuildingService buildingService;

	public String getYM() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		return dateFormat.format(date);
	}

	// 로그인한 사용자(임대인)의 건물들을 목록으로 출력하고, 해당 건물의 전월, 금월 관리비 출력 화면으로 이동
	@GetMapping("gwanlibiList")
	public String gwanlibiList(Model model, BuildingPageDTO dto) {
		// public String gwanlibiList(Model model, BuildingPageDTO dto,
		// @RequestParam(name = "imdaeinId", value = "user02") String imdaeinId) {
		LoginInfoVO login = SecurityUtil.getLoginInfo();
		// System.out.println(login.getLoginId());

		// 한 페이지당 출력할 건물 개수 - 페이징
		dto.setAmount(6);

		// 출력할 건물의 총 개수
		int total = gwanlibiService.buildingTotalCount(login.getLoginId());
		// int total = gwanlibiService.buildingTotalCount(imdaeinId);
		dto.setTotal(total);

		List<GwanlibiVO> list = gwanlibiService.monthGwanlibiByBuildingList(login.getLoginId(), dto);
		// List<GwanlibiVO> list =
		// gwanlibiService.monthGwanlibiByBuildingList(imdaeinId, dto);

//		for(GwanlibiVO vo : list) {
//			System.err.println(vo.getPaymentMonth());
//			if(vo.getPaymentMonth() == null) {
//				vo.setPaymentMonth(getYM());
//			}
//		}

		model.addAttribute("list", list);
		model.addAttribute("page", dto);

		return "gwanlibi/gwanlibiList";
	}

	// 관리비 항목 리스트
	@GetMapping("itemList")
	public String itemList(@RequestParam(name = "buildingId") String buildingId, Model model) {

		List<GwanlibiVO> list = gwanlibiService.itemList(buildingId);
		System.err.println(list.size());

		if (list.size() <= 0) {
			List<GwanlibiVO> basicList = gwanlibiService.basicGwanlibiList();
			basicList.forEach(ele -> {
				ele.setVariableYn("변동");
				ele.setBuildingId(buildingId);
			});

			model.addAttribute("list", basicList);
			System.err.println('?');
			System.err.println(basicList);
		} else {
			model.addAttribute("list", list);
		}

		System.err.println(buildingId);

		model.addAttribute("buildingId", buildingId);

		return "gwanlibi/itemList";
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
	@GetMapping("detailsBillList")
	public String detailsBillList(Model model, GwanlibiVO gwanlibiVO, BuildingVO buildingVO) {

		// 관리비 상세 내역 목록
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(gwanlibiVO);
		System.err.println(detailsList);
		// 빌딩 정보 가져오기. - 단건 조회
		BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);

		// 가구별 관리비 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiByGagu();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiByGagu(result);
		}

		// 관리 비용 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiItemMoney();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiItemMoney(result);
		}

		model.addAttribute("detailsList", detailsList);
		model.addAttribute("buildingInfo", buildingInfo);
		return "gwanlibi/detailsBillList";
	}

	// 월별 건물별 관리비 상세 내역 출력 화면으로 이동- ajax
	@PostMapping("detailsBillList")
	@ResponseBody
	public Map<String, Object> reDetailsBillList(GwanlibiVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(vo);
		// 가구별 관리비 number format 설정.
		for (GwanlibiVO list : detailsList) {
			System.err.println("여기");
			double gwanlibi = list.getGwanlibiByGagu();
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiByGagu(result);
		}

		// 관리 비용 number format 설정.
		for (GwanlibiVO list : detailsList) {
			double gwanlibi = list.getGwanlibiItemMoney();
			System.err.println(gwanlibi);
			String result = NumberFormat.getInstance().format(gwanlibi) + " 원";
			list.setStrGwanlibiItemMoney(result);
		}

		map.put("detailsList", detailsList);
		return map;
	}
	
	// 건물별 관리비 정산 화면 선택
	@PostMapping("gwanlibiCalculationForm")
	@ResponseBody
	public Map<String, Object> insertGwanlibi(@RequestParam(name = "buildingId") String buildingId, Model model,
			HttpServletResponse response, GwanlibiVO gwanlibiVO, BuildingVO buildingVO) {		
		
		Map<String, Object> map = new HashMap<String, Object>();

		// 관리비 항목 설정하지 않았으면 알림 띄우고, 관리비 항목 설정으로 이동
//		if (list.size() == 0) {
//			try {
//				response.setContentType("text/html; charset=utf-8");
//				PrintWriter w = response.getWriter();
//				String msg = "관리비 항목이 없습니다. 먼저 관리비 항목을 설정해주세요.";
//				String url = "itemList?buildingId=" + buildingId;
//				w.write("<script>alert('" + msg + "');location.href='" + url + "';</script>");
//				w.flush();
//				w.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			model.addAttribute("list", list);
//		}
		//System.err.println(list.size());
		
		// 설정된 관리비 항목 리스트의 데이터 개수
		List<GwanlibiVO> gwanlibiItmeList = gwanlibiService.itemList(buildingId);
		int gwanlibiItmeListSize = gwanlibiItmeList.size();
		map.put("gwanlibiItmeListSize", gwanlibiItmeListSize);
		
		// 이미 관리비 정산을 했으면 수정 화면으로, 아니면 등록 화면으로.
		int dataCnt = gwanlibiService.getCountingMonthGwanlibiData(buildingId);
		if (dataCnt <= 0) {
			//model.addAttribute("buildingId", buildingId);
			//return "/insertGwanlibi?buildingId=" + buildingId;
			String url = "/insertGwanlibi?buildingId=" + buildingId;
			map.put("url", url);
		} else {
			// 관리비 상세 내역 목록
//			List<GwanlibiVO> detailsList = gwanlibiService.detailsBillList(gwanlibiVO);
//			System.err.println(detailsList);
//			
//			// 빌딩 정보 가져오기. - 단건 조회
//			BuildingVO buildingInfo = buildingService.buildingInfo(buildingVO);
//			
//			model.addAttribute("detailsList", detailsList);
//			model.addAttribute("buildingInfo", buildingInfo);
			
			//return "/updateGwanlibi";
			// paymentMont -> 전월
			Date now = new Date();		
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(now);
			//cal.add(Calendar.MONTH, -1);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			String paymentMonth = year + "-" + month;
			
			
			String url = "/updateGwanlibi?buildingId=" + buildingId + "&paymentMonth=" + paymentMonth;
			map.put("url", url);
		}
		return map;
	}

	
	// TODO
	@GetMapping("insertGwanlibi")
	public String insertGwanlibiForm(@RequestParam(name = "buildingId", required=false) String buildingId, Model model,
			GwanlibiVO gwanlibiVO, BuildingVO buildingVO) {
		List<GwanlibiVO> list = gwanlibiService.itemList(buildingId);
		model.addAttribute("list", list);
		model.addAttribute("buildingId", buildingId);
		return "gwanlibi/insertGwanlibi";
	}
	
	
	// 관리비 등록 - 아작스
	@PostMapping("insertGwanlibi")
	@ResponseBody
	public String insertGwanlibi(@RequestBody List<GwanlibiVO> gridDatalist) {

		gwanlibiService.addGwanlibi(gridDatalist);

		String buildingId = "";

		for (GwanlibiVO item : gridDatalist) {
			buildingId = item.getBuildingId();
		}

		// 전월 구하기
		// TODO : 1월이면 12로 변경해주기.
		Date now = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String ym = year + "-" + month;
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		// String lastMonth = simpleDateFormat.format(ym);

		return "/detailsBillList?buildingId=" + buildingId + "&paymentMonth=" + ym;
	}
	
	// TODO - 업데이트 화면
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
	
	// TODO - 업데이트 기능
	@PostMapping("updateGwanlibi")
	@ResponseBody
	public void updateGwanlibiProcess(@RequestBody List<GwanlibiVO> gridDatalist) {
		//int result = 
				gwanlibiService.modifyGwanlibi(gridDatalist);
		//return result;
	}

}