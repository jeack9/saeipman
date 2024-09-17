package com.saeipman.app.room.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstractVO {
	private String constractNo; //유니크키
	private String constractType; // 계약 유형 월세/전세
	private int deposit; // 계약된 보증금
	private int mRent; // 계약된 월세
	private String payType; // 월세유형 선후불
	private String imchainName; // 임차인 이름
	private String imchainPhone; // 임차인 연락처
	private String imchainAccount; // 임차인 계좌
	private String imdaeinAccount; // 임대인 계좌
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	private Date constractDate; // 계약일
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	private Date expDate; // 만료일
	private int constractState; // 계약 상태(-1 만료, 0 예정, 1 계약)
	private String constractFile; // OCR용 파일
	private String mRentDate; // 월세 납부일
	private String memo; // 비고
	private String roomId; // 방 아이디 fk
	public int getmRent() {
		return mRent;
	}
	public void setmRent(int mRent) {
		this.mRent = mRent;
	}
	public String getmRentDate() {
		return mRentDate;
	}
	public void setmRentDate(String mRentDate) {
		this.mRentDate = mRentDate;
	}
	
}
