package com.saeipman.app.commom.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saeipman.app.room.service.BuildingContractExcel;
import com.saeipman.app.room.service.ConstractService;
import com.saeipman.app.room.service.RoomService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final ConstractService constractService;

    
    // 날짜 포맷변환
    private String formatDate(Date date) {
    	if(date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @GetMapping("/exports/constracts")
    public void exportConstractsToExcel(HttpServletResponse response, String buildingId) throws IOException {
        List<BuildingContractExcel> constracts = constractService.buildingConstractExcel(buildingId);
        String buildingName = constracts.get(0).getBuildingName();
        // 현재 날짜 구하기
        LocalDate currentDate = LocalDate.now();
        // 원하는 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 날짜를 문자열로 변환
        String formattedDate = currentDate.format(formatter);

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(formattedDate);
        Row headerRow = sheet.createRow(0);
        
        // 셀 스타일 생성
        CellStyle headerStyle = workbook.createCellStyle();
        // 배경색 설정
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // 헤더 셀 생성
        String[] headers = {"건물명", "호실", "계약유형", "보증금", "계약일", "만료일", "월세", "선불/후불", "월세납부일", "임차인명", "연락처", "비고"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle); // 각 셀에 스타일 적용
        }

        // 데이터 추가
        int rowNum = 1;
        for (BuildingContractExcel constract : constracts) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(constract.getBuildingName());     // 건물명
            row.createCell(1).setCellValue(constract.getRoomNo());           // 호실
            row.createCell(2).setCellValue(constract.getConstractType());	 // 계약유형
            row.createCell(3).setCellValue(constract.getDeposit()); 		 // 보증금
            row.createCell(4).setCellValue(formatDate(constract.getConstractDate()));     // 계약일
            row.createCell(5).setCellValue(formatDate(constract.getExpDate()));     // 만료일
            row.createCell(6).setCellValue(constract.getmRent());     // 월세
            row.createCell(7).setCellValue(constract.getPayType());     // 선불/후불
            String mRentDate = constract.getmRentDate() > 0 ?  constract.getmRentDate() + " 일" : "";
            row.createCell(8).setCellValue(mRentDate);     // 월세 납부일
            row.createCell(9).setCellValue(constract.getImchainName());     // 임차인명
            row.createCell(10).setCellValue(constract.getImchainPhone());    // 연락처
            row.createCell(11).setCellValue(constract.getMemo());    // 비고
        }

        // 컨텐츠 타입 설정 및 파일 이름 지정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=constracts.xlsx");
        // 엑셀 파일을 클라이언트에게 전달
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
