package com.saeipman.app.message;


import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class MsgService {
	@Value("${coolsms.api.key}")
	private String apiKey;

	@Value("${coolsms.api.secret}")
	private String apiSecret;

	@Value("${coolsms.api.number}")
	private String fromPhoneNumber;
	
	private DefaultMessageService messageService;
	
	@PostConstruct
	private void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}
	
	
	
	public void sendOne(String toFromPhoneNumber, String msg) {
		Message message = new Message();
		
		message.setFrom(fromPhoneNumber);
		message.setTo(toFromPhoneNumber);
		message.setText(msg);
		
		//SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        //System.out.println(response);
		
		this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 메시지 발송 요청
	                

	}
}
