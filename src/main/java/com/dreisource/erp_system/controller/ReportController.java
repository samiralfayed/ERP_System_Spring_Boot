package com.dreisource.erp_system.controller;


import com.dreisource.erp_system.model.Report;
import com.dreisource.erp_system.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ✅ Create a new Report
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // ✅ Update an existing Report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report) {
        Optional<Report> updatedReport = reportService.updateReport(id, report);
        return updatedReport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    // ✅ Get all Reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }

    // ✅ Get Report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // ✅ Delete Report by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable Long id) {
        boolean isDeleted = reportService.deleteReport(id);
        if (isDeleted) {
            return ResponseEntity.ok("Report deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found!");
    }

    // ✅ Get Reports by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Report>> getByCategory(@PathVariable String category) {
        List<Report> reports = reportService.getReportsByCategory(category);
        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }

    // ✅ Get Reports by Source
    @GetMapping("/source/{source}")
    public ResponseEntity<List<Report>> getBySource(@PathVariable String source) {
        List<Report> reports = reportService.getReportsBySource(source);
        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }

    // ✅ Get Reports by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Report>> getByStatus(@PathVariable String status) {
        List<Report> reports = reportService.getReportsByStatus(status);
        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }

    // ✅ Get Reports by Author
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Report>> getByAuthor(@PathVariable String author) {
        List<Report> reports = reportService.getReportsByAuthor(author);
        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }
        return ResponseEntity.ok(reports);
    }
}