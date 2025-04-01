package com.dreisource.erp_system.serviceimpl;


import com.dreisource.erp_system.model.Report;
import com.dreisource.erp_system.repository.ReportRepository;
import com.dreisource.erp_system.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Optional<Report> updateReport(Long id, Report updatedReport) {
        return reportRepository.findById(id).map(existingReport -> {
            existingReport.setTitle(updatedReport.getTitle());
            existingReport.setDescription(updatedReport.getDescription());
            existingReport.setCategory(updatedReport.getCategory());
            existingReport.setSource(updatedReport.getSource());
            existingReport.setStatus(updatedReport.getStatus());
            existingReport.setAuthor(updatedReport.getAuthor());
            return reportRepository.save(existingReport);
        });
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public boolean deleteReport(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Report> getReportsByCategory(String category) {
        return reportRepository.findByCategory(category);
    }

    @Override
    public List<Report> getReportsBySource(String source) {
        return reportRepository.findBySource(source);
    }

    @Override
    public List<Report> getReportsByStatus(String status) {
        return reportRepository.findByStatus(status);
    }

    @Override
    public List<Report> getReportsByAuthor(String author) {
        return reportRepository.findByAuthor(author);
    }
}