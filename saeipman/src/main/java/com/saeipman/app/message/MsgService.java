package com.saeipman.app.message;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.service.LesseeInfoVO;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
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
	
	// 경민
	public void sendGroup(List<LesseeInfoVO> list, String msg) {
		List<Message> messageList = new ArrayList<Message>();
		
		for(LesseeInfoVO item : list) {
			if (item.getImchainPhone().length() > 9 && item.getImchainPhone().length() < 12) {
				System.err.println("===============================");
				System.err.println("Send To : " + item.getImchainPhone());
				
				Message message = new Message();
				
				message.setFrom(fromPhoneNumber);
				message.setTo(item.getImchainPhone());
				message.setText(msg);
				
				
				messageList.add(message);
				
				// 테스트 환경
				System.err.print(messageList.toString());
				
//				try {
//					// 테스트 환경
//					System.out.print(messageList.toString());
//					// 실제 사용 시에만 주석 풀고 사용하세용
//					// this.messageService.send(messageList);
//				} catch (NurigoMessageNotReceivedException e) {
//					System.out.println("NurigoMessageNotReceivedException : " + e.getMessage());
//				} catch (NurigoEmptyResponseException e) {
//					System.out.println("NurigoEmptyResponseException : " + e.getMessage());
//				} catch (NurigoUnknownException e) {
//					System.out.println("NurigoUnknownException : " + e.getMessage());
//				}
			} else {
				return;
			}
		}
		
	}
}
