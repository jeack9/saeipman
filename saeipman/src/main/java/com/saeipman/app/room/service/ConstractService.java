package com.saeipman.app.room.service;


public interface ConstractService {
	// 임차인아이디 - 계약단건 조회
	public ConstractVO constractInfoImchain(String imcahinId);
	
	// 계약번호 - 계약단건 조회
	public ConstractVO constractInfo(String constractNo);
}
