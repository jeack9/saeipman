package com.saeipman.app.support.service;

import lombok.Data;

@Data
public class SearchQna {
	private String keyword = "";
	private Integer answerState;
	private int page = 1;
	private int recordSize = 5;
	private String writerId;
	private int auth;
}
