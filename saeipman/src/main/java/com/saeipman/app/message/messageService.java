//package com.saeipman.app.message;
//
//
//import java.util.HashMap;
//
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import jakarta.annotation.PostConstruct;
//
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.model.Message;
//import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import net.nurigo.sdk.message.service.DefaultMessageService;
//
//
//
//@Service
//public class messageService {
//	
//	@Value("${coolsms.api.key}")
//	private String apiKey;
//
//	@Value("${coolsms.api.secret}")
//	private String apiSecret;
//
//	@Value("${coolsms.api.number}")
//	private String fromPhoneNumber;
//	
//	 private DefaultMessageService messageService;
//	 
//	 @PostConstruct
//	    private void init(){
//	        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
//	    }
//	 
//	   public void sendSMS(){
//	        Message message = new Message(); // 새 메시지 객체 생성
//	        message.setFrom(fromPhoneNumber); // 발신자 번호 설정
//	        message.setTo("010"); // 수신자 번호 설정
//	        message.setText("본인확인 인증번호는 백민주 입니다."); // 메시지 내용 설정
//
//	        this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 메시지 발송 요청
//	    }
//	
//	
//
//}
