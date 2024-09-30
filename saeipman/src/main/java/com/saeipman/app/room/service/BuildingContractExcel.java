package com.saeipman.app.room.service;

import java.util.Date;

import lombok.Data;
@Data
public class BuildingContractExcel {
	
	private String buildingName;
	private String roomNo;
	private String constractType;
	private String deposit;
	private Date constractDate;
	private Date expDate;
	private String mRent;
	private String payType;
	private int mRentDate;
	private String imchainName;
	private String imchainPhone;
	private String memo;
	public String getmRent() {
		return mRent;
	}
	public void setmRent(String mRent) {
		this.mRent = mRent;
	}
	public int getmRentDate() {
		return mRentDate;
	}
	public void setmRentDate(int mRentDate) {
		this.mRentDate = mRentDate;
	}
	
}
