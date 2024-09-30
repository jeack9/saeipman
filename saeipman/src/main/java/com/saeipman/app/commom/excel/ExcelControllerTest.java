package com.saeipman.app.commom.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ExcelControllerTest {

    // 예시 데이터 생성
    private List<String[]> getSampleData() {
        return List.of(
            new String[]{"ID", "Name", "Age"},
            new String[]{"1", "John Doe", "25"},
            new String[]{"2", "Jane Smith", "30"},
            new String[]{"3", "Michael Brown", "35"}
        );
    }

    // 엑셀 다운로드를 위한 API
    @GetMapping("/download/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        // Excel Workbook 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sample Data");

        // 샘플 데이터
        List<String[]> data = getSampleData();

        // 데이터 작성
        int rowCount = 0;
        for (String[] rowData : data) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            for (String field : rowData) {
                Cell cell = row.createCell(columnCount++);
                cell.setCellValue(field);
            }
        }

        // 파일 다운로드를 위한 응답 헤더 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sample.xlsx\"");

        // 엑셀 파일을 응답으로 출력
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}