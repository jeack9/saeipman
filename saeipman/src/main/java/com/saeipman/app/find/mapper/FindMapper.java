package com.saeipman.app.find.mapper;

import java.util.List;

import com.saeipman.app.find.service.FindVO;

public interface FindMapper {
	public FindVO selectId(FindVO findVO);
	public FindVO seletPw(FindVO findVO);
	public String updatePw(FindVO fivdVo);
}
