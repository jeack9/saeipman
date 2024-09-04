package com.saeipman.app.noticeBuilding.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class NoticeBuildingVO {
	private Integer postNo; // 글 번호
	private String title;
	private String writer;
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date regDate; // 작성일자
	private Date modDate; // 수정일자
	private String chumbuImage; // 첨부이미지
	private Integer views; // 조회수
	private String buildingId; // 건물아이디

	public String[] getChumbuImages() {
		if (chumbuImage == null) {
			return null;
		} else {
			return chumbuImage.split(":");
		}
	}
}
