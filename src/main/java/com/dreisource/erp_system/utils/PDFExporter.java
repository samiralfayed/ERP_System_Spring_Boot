package com.dreisource.erp_system.utils;


import com.dreisource.erp_system.model.Report;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFExporter {

    public static byte[] exportReportsToPDF(List<Report> reports) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph title = new Paragraph("Reports", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Table
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        addTableHeader(table);
        addTableData(table, reports);
        document.add(table);

        document.close();
        return outputStream.toByteArray();
    }

    private static void addTableHeader(PdfPTable table) {
        String[] headers = {"ID", "Title", "Category", "Source", "Author", "Status"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            table.addCell(cell);
        }
    }

    private static void addTableData(PdfPTable table, List<Report> reports) {
        for (Report report : reports) {
            table.addCell(String.valueOf(report.getId()));
            table.addCell(report.getTitle());
            table.addCell(report.getCategory());
            table.addCell(report.getSource());
            table.addCell(report.getAuthor());
            table.addCell(report.getStatus());
        }
    }
}

