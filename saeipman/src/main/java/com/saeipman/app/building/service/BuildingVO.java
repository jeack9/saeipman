package com.saeipman.app.building.service;

import java.util.List;

import com.saeipman.app.gwanlibi.service.GwanlibiPaymentVO;
import com.saeipman.app.room.service.RoomVO;

import lombok.Data;

@Data
public class BuildingVO {
	private String buildingId;
	private String buildingName;
	private String doroAddr;
	private String jibunAddr;
	private String detailsAddr;
	private Integer gunchookArea;
	private Integer totalFloor;
	private Integer floorGaguCount;
	private Integer totalGagu;
	private Integer parking;
	private String liftYn;
	private String delayAlertYn;
	private String minwonAlertYn;
	private String groupId;
	private String ocrFileName;
	private String memo;
	private String imdaeinId;
	private String floorArea;
	private String floorAreaRatio;
	private String buildingHeigh;
	
	private List<String> fileName;
	
	private List<RoomVO> rooms;
	
	// 경민
	private List<GwanlibiPaymentVO> roomList;

}
