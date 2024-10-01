package com.saeipman.app.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saeipman.app.gwanlibi.service.GwanlibiMsgVO;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
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

		// SingleMessageSentResponse response = this.messageService.sendOne(new
		// SingleMessageSendingRequest(message));
		// System.out.println(response);

		this.messageService.sendOne(new SingleMessageSendingRequest(message)); // 메시지 발송 요청

	}

	
	
	// 경민
	public void sendGroup(List<GwanlibiMsgVO> imchainInfoList, String imdaeinPhoneNumber, String msg) {

		System.err.println("임차인 몇 명? = " +imchainInfoList.size());
		

		// 예외처리 - 임차인 정보가 없으면 리턴.
		if (imchainInfoList.size() <= 0) {			
			return;
			
		// 예외처리 - 임차인이 한 명일 경우 단건 처리.
		} else if (imchainInfoList.size() == 1) {
			// 유효성 검사 - 임차인 휴대 전화 번호가 10 ~ 11 자리일 경우만.
			if (imchainInfoList.get(0).getImchainPhone().length() > 9 && imchainInfoList.get(0).getImchainPhone().length() < 12) {
				
				Message message = new Message();
				
				message.setFrom(imdaeinPhoneNumber);
				message.setTo(imchainInfoList.get(0).getImchainPhone());
				message.setText(msg);
				
				// 테스트 환경.
				System.err.print(message.toString());
				
//				try {
//					// 실제 사용 시에만 주석 풀고 사용하자아!
//					this.messageService.send(message);
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

		// 예외처리 - 여러 건일 경우.
		} else {
			
			for (GwanlibiMsgVO imchain : imchainInfoList) {
				// 유효성 검사 - 임차인 휴대 전화 번호가 10 ~ 11 자리일 경우만.
				if (imchain.getImchainPhone().length() > 9 && imchain.getImchainPhone().length() < 12) {
					
					Message message = new Message();
					
					System.err.println("임차인 번호 = " + imchain.getImchainPhone());
					System.err.println("임대인 번호 = " + imdaeinPhoneNumber);

					message.setFrom(imdaeinPhoneNumber);
					message.setTo(imchain.getImchainPhone());
					message.setText(msg);

					// 테스트 환경.
					System.err.println("***메세지 리스트***");
					System.err.print(message.toString());

//					try {
//						// 실제 사용 시에만 주석 풀고 사용하자아!
//						this.messageService.send(message);
//					} catch (NurigoMessageNotReceivedException e) {
//						System.out.println("NurigoMessageNotReceivedException : " + e.getMessage());
//					} catch (NurigoEmptyResponseException e) {
//						System.out.println("NurigoEmptyResponseException : " + e.getMessage());
//					} catch (NurigoUnknownException e) {
//						System.out.println("NurigoUnknownException : " + e.getMessage());
//					}
					
				} else { // 유효성 조건에 맞지 않으면 리턴. 
					return;
				} // end of if 유효성 검사				
			} // end of for(imchainInfoList)
			
		}

	} // end of sendGroup()
}
