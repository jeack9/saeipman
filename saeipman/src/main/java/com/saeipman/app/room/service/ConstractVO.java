package com.saeipman.app.room.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstractVO {
	private String constractNo; //유니크키
	@NotBlank(message = "계약 유형은 필수입니다.")
	private String constractType; // 계약 유형 월세/전세
	@Min(value = 0, message = "보증금은 0 이상이어야 합니다.")
	private int deposit; // 계약된 보증금
	@Min(value = 0, message = "월세는 0 이상이어야 합니다.")
	private int mRent; // 계약된 월세
	@NotBlank(message = "월세 유형은 필수입니다.")
	private String payType; // 월세유형 선후불
	@NotBlank(message = "임차인 이름은 필수입니다.")
	private String imchainName; // 임차인 이름
	@Pattern(regexp = "^(010|011|016|017|018|019)[0-9]{7,8}$", message = "유효한 전화번호를 입력하세요.")
	private String imchainPhone; // 임차인 연락처
	@NotBlank(message = "임차인 계좌는 필수입니다.")
	@Size(min = 10, max = 20, message = "계좌번호는 10자 이상 20자 이하이어야 합니다.")
	private String imchainAccount; // 임차인 계좌
	@NotBlank(message = "임대인 계좌는 필수입니다.")
	@Size(min = 10, max = 20, message = "계좌번호는 10자 이상 20자 이하이어야 합니다.")
	private String imdaeinAccount; // 임대인 계좌
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	@NotNull(message = "계약일은 필수입니다.")
	private Date constractDate; // 계약일
	@DateTimeFormat(pattern =  "yyyy-MM-dd")
	@NotNull(message = "만료일은 필수입니다.")
	private Date expDate; // 만료일
	@NotNull(message = "계약 상태는 필수입니다.")
	private int constractState; // 계약 상태(-1 만료, 0 예정, 1 계약)
	private String constractFile; // OCR용 파일
	@NotBlank(message = "월세 납부일은 필수입니다.")
	private String mRentDate; // 월세 납부일
	private String memo; // 비고
	@NotBlank(message = "방 아이디는 필수입니다.")
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
