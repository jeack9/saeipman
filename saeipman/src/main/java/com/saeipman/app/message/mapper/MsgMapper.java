package com.saeipman.app.message.mapper;

import com.saeipman.app.message.MessageVO;


public interface MsgMapper {
	public String selectMinwonMsg(int msgType);
	
	public String selectPhone(String roomId); //임대인 연락처(민원)
}
