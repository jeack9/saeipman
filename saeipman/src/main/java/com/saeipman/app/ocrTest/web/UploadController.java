package com.saeipman.app.ocrTest.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.saeipman.app.ocrTest.config.OcrApi;
import com.saeipman.app.ocrTest.config.OcrUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UploadController {
    @Value("${naver.service.secretKey}")
    private String secretKey;
    private final OcrApi naverApi;
    private final OcrUtil ocrUtil;

    // 파일 업로드 폼을 보여주기 위한 GET 요청 핸들러 메서드
    @GetMapping("/upload-form")
    public String uploadForm() throws Exception {
        return "ocr/upload-form"; // HTML 템플릿의 이름을 반환 (upload-form.html)
    }

    // 파일 업로드 및 OCR 수행을 위한 POST 요청 핸들러 메서드
    @PostMapping("/uploadAndOcr")
    public String uploadAndOcr(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            return "error"; // 파일이 비어있을 경우 에러를 처리하는 HTML 템플릿으로 이동
        }

        String naverSecretKey = secretKey; // 본인의 네이버 Clova OCR 시크릿 키로 대체

        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        List<String> result = naverApi.callApi("POST", tempFile.getPath(), naverSecretKey, "jpg");

        tempFile.delete(); // 임시 파일 삭제
        
		/*
		 * //ocr 병합 String merged = ocrUtil.mergeWords(result);
		 * 
		 * //원하는 정보 String doro = ocrUtil.extractValue(merged, "도로명주소"); String area =
		 * ocrUtil.extractValue(merged, "건축면적"); String saede =
		 * ocrUtil.extractValue(merged, "세대수");
		 * 
		 * model.addAttribute("doro",doro); model.addAttribute("area",area);
		 * model.addAttribute("saede",saede);
		 */
        // 키워드를 기준으로 전체 주소 추출
        
        //String merged = ocrUtil.mergeWords(result);
        String fullAddress = ocrUtil.mergeWords(result, "도로명주소").split("대지면적")[0];
        String gunchookArea = ocrUtil.mergeWords(result, "건축면적").split("※건폐율")[0];
        String saedaeArea = ocrUtil.mergeWords(result, "호수/가구수/세대수").split("대지위치")[0];
        String heighArea = ocrUtil.mergeWords(result, "높이").split("※조경면적")[0];
        
        int firstM2 = gunchookArea.indexOf("m2");
        int lastM2 = gunchookArea.lastIndexOf("m2");
        int lastFloor = gunchookArea.lastIndexOf("층");
        int lastSaedae = saedaeArea.lastIndexOf("세대");
        int lastHeigh = heighArea.lastIndexOf("m");
        
        String gunchook = gunchookArea.substring(0,firstM2);
        String floorAreaRatio = gunchookArea.substring(0,lastM2);
        String floor = gunchookArea.substring(0,lastFloor);
        String saedae = saedaeArea.substring(0,lastSaedae);
        String heigh = heighArea.substring(0,lastHeigh);
        
        int gunchookLastPos = gunchook.lastIndexOf(" ");
        int floorAreaRatioLastPos = floorAreaRatio.lastIndexOf(" ");
        int floorLastPos = floor.lastIndexOf(" ");
        int saedaeLastPos = saedae.lastIndexOf(" ");
        int heighLastPos = heigh.lastIndexOf(" ");
        
        gunchook = gunchookArea.substring(gunchookLastPos,firstM2).trim();
        floorAreaRatio = gunchookArea.substring(floorAreaRatioLastPos,lastM2).trim();
        floor = gunchookArea.substring(floorLastPos,lastFloor).trim();
        saedae = saedaeArea.substring(saedaeLastPos,lastSaedae).trim();
        heigh = heighArea.substring(heighLastPos,lastHeigh).trim();
        
        model.addAttribute("fullAddress", fullAddress);
        model.addAttribute("gunchook", gunchook);
        model.addAttribute("floorAreaRatio", floorAreaRatio);
        model.addAttribute("floor", floor);
        model.addAttribute("saedae", saedae);
        model.addAttribute("heigh", heigh);
        


        model.addAttribute("ocrResult", result); // OCR 결과를 HTML 템플릿에 전달

        return "ocr/ocr-result"; // OCR 결과를 표시하는 HTML 템플릿 이름 반환
    }
    
}
