package com.saeipman.app.noticeBuilding.service;

import java.util.Date;
import java.util.List;

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
	private String groupId; // 첨부이미지
	private List<String> fileName;
	private Integer views; // 조회수
	private String buildingId; // 건물아이디
	private String buildingName;// 건물 이름
	private String imdaeinId; // 임대인 아이디

	public String[] getGroupIds() {
		if (groupId == null) {
			return null;
		} else {
			return groupId.split(":");
		}
	}
}
