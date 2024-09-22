package com.saeipman.app.find.service;


public interface FindService {
	public FindVO idSelect(FindVO findVO); //아이디 찾기
	public FindVO pwSelect(FindVO findVO); //임대인 비번 조회
	public FindVO imchainPw(FindVO findVO); //임차인 비번 조회
	public int pwUpdate(FindVO findVO);
}
