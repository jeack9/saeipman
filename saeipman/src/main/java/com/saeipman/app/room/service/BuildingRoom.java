package com.saeipman.app.room.service;

import java.util.Date;

import lombok.Data;

@Data
public class BuildingRoom {
	private String buildingName;
	private String constractType;
	private int ipjuState;
	private int roomNo;
	private String imchainName;
	private int deposit;
	private int mRent;
	private Date constractDate;
	private Date expDate;
	private String memo;
	private String constractNo;
	private String roomId;
	private String imdaeinId;
	private String buildingId;
	private int reserveCnt; // 입주예정 확인 0 이상 입주 예정 있음.
}
