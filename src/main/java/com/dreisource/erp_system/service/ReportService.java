package com.dreisource.erp_system.service;


import com.dreisource.erp_system.model.Report;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport(Report report);
    Report saveReport(Report report); // Ensure this is added
    Optional<Report> updateReport(Long id, Report report);
    List<Report> getAllReports();
    Optional<Report> getReportById(Long id);
    boolean deleteReport(Long id);
    List<Report> getReportsByCategory(String category);
    List<Report> getReportsBySource(String source);
    List<Report> getReportsByStatus(String status);
    List<Report> getReportsByAuthor(String author);
}