package com.saeipman.app.ocrTest.config;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OcrUtil {
//
	public String mergeWords(List<String> ocrWords, String keyword) {

		// StringBuilder merge = new StringBuilder();// 문자 병합

		StringBuilder merge = new StringBuilder();
		boolean isLine = false;
//		for (String word : ocrWords) {
//			merge.append(word).append(" ");
//		}
//		return merge.toString().trim();

		for (String word : ocrWords) {
			if (isLine) {

				// 키워드 이후의 단어들을 병합
				merge.append(word).append(" ");
//
//		            if (word.matches("[가-힣0-9]+")) {  // 한글 및 숫자만 병합
//		            	merge.append(word).append(" ");
//		            } else {
//		                // 더 이상 키워드로로 판단되지 않는 경우 종료
//		                break;
//		            }
			}
			if (word.equals(keyword)) {
				isLine = true; // 키워드가 등장하면 병합 시작
			}
		}

		return merge.toString().trim();

	}

//	public String extractValue(String text, String keyword) {
//		int startIndex = text.indexOf(keyword);
//
//		if (startIndex != -1) {
//			int endIndex = text.indexOf("\n", startIndex);
//
//			if (endIndex == -1) {
//				endIndex = text.length();
//			}
//
//			String value = text.substring(startIndex + keyword.length(), endIndex).trim();
//			return value.split(" ")[0];
//		}
//		return "";
//	}
}
