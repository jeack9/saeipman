package com.saeipman.app.room.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saeipman.app.commom.paging.PagingDTO;
import com.saeipman.app.commom.security.SecurityUtil;
import com.saeipman.app.room.service.BuildingRoom;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.ConstractVO;
import com.saeipman.app.room.service.RoomService;
import com.saeipman.app.room.service.RoomVO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {
	private final RoomService rsvc;
	private final ConstractService csvc;
	
	@GetMapping("roomListBackup")
	public void roomP(Integer page, String buildingId, Model model) {
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
	};
	
	@PostMapping("getConstract")
	public String getConstract(@RequestBody HashMap<String, Object> map, Model model) {
		ConstractVO constractVo = new ConstractVO();
		if(map.get("constractNo") != null) {
			constractVo = csvc.constractInfo((String)map.get("constractNo"));
		}
		RoomVO roomVo = rsvc.roomInfo((String)map.get("roomId"));
		model.addAttribute("constractVo", constractVo);
		model.addAttribute("roomVo", roomVo);
		model.addAttribute("buildingName", (String)map.get("buildingName"));
		model.addAttribute("ipjuState", map.get("ipjuState"));
		return "room/fragments/constractModal :: modalContent";
	}
	
}
