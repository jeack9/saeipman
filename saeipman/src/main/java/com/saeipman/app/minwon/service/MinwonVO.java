package com.saeipman.app.minwon.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MinwonVO {
	private Integer postNo; // 민원글번호
	private String title;
	private String content;
	private String category;
	private String groupId; // 첨부이미지
	private String roomNo; // 방번호
	private String acceptState; // 처리상태

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Timestamp visitsDate; // 방문일자
	
	private Date regDate; // 작성일자
	private Date modDate; // 수정일자
	private Integer alertType; //알림유형
	private String roomId; //방 아이디
	private String buildingId;
	
	private int categoryNo;
	private String categoryType;
	
	public String[] getChumbuImages() {
		if(groupId == null) return null;
		return groupId.split(":");
	}
}
