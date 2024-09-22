package com.saeipman.app.find.mapper;

import java.util.List;

import com.saeipman.app.find.service.FindVO;

public interface FindMapper {
	public FindVO selectId(FindVO findVO);
	public FindVO selectPw(FindVO findVO); //임대인 비번 조회
	public FindVO imchainPw(FindVO findVO); //임차인 비번 조회
	public int updatePw(FindVO joinVO);
}
