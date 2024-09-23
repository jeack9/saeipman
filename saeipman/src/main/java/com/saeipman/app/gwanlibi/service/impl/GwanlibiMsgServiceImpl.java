package com.saeipman.app.gwanlibi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.mapper.GwanlibiMsgMapper;
import com.saeipman.app.gwanlibi.service.GwanlibiMsgService;
import com.saeipman.app.gwanlibi.service.GwanlibiMsgVO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GwanlibiMsgServiceImpl implements GwanlibiMsgService {
	
	private GwanlibiMsgMapper gwanlibiMsgMapper;
	
	@Override
	public List<GwanlibiMsgVO> getImchainPhoneNumber(String buildingId) {
		return gwanlibiMsgMapper.selectImcahinPhoneNumber(buildingId);
	}

	/*
	 * @Override public GwanlibiMsgVO getImdaeinPhoneNumber(String imdaeinId) {
	 * gwanlibiMsgMapper.selectImdaeinPhoneNumber(imdaeinId); }
	 */
	@Override
	public String getImdaeinPhoneNumber(String imdaeinId) {
		return gwanlibiMsgMapper.selectImdaeinPhoneNumber(imdaeinId);
	}

	@Override
	public List<GwanlibiMsgVO> getGwanlibiOverdueImchainList(String buildingId) {		
		return gwanlibiMsgMapper.selectGwanlibiOverdueImchainList(buildingId);
	}

}
