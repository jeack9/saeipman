package com.saeipman.app.room.service.impl;

import org.springframework.stereotype.Service;

import com.saeipman.app.room.mapper.ConstractMapper;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.ConstractVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ConstractServiceImpl implements ConstractService{
	private final ConstractMapper cmapper;
	@Override
	public ConstractVO constractInfoImchain(String imcahinId) {
		return cmapper.selectConstractImchain(imcahinId);
	}

}
