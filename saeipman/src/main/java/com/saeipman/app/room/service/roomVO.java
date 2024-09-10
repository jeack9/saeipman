package com.saeipman.app.room.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class roomVO {
	private String roomId; // 방 아이디 프라이머리
	private Integer roomNo; // 방 호실
	private int floor; // 층
	private int ipjuState; // 입주 상태(-1 공실, 1 입주)
	private String buildingId; // 건물 아이디 fk
	private int deposit; // 기본 보증금
	private int mRent; // 기본 월세
}
