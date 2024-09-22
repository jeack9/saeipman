package com.saeipman.app.message;

import lombok.Data;

@Data
public class MessageVO {
	private int alertType; //알림 타입(0은 민원, 1은 연체)
	private String alertMsg;
	
	private String roomId;
	
	
}
