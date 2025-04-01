package com.dreisource.erp_system.utils;


import com.dreisource.erp_system.model.Report;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public static byte[] exportReportsToExcel(List<Report> reports) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reports");

        // Header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Title", "Category", "Source", "Created By", "Created At"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Data rows
        int rowIdx = 1;
        for (Report report : reports) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(report.getId());
            row.createCell(1).setCellValue(report.getTitle());
            row.createCell(2).setCellValue(report.getCategory());
            row.createCell(3).setCellValue(report.getSource());
            row.createCell(4).setCellValue(report.getCreatedBy());
            row.createCell(5).setCellValue(report.getCreatedAt().toString());
        }

        // Convert to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private static CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}