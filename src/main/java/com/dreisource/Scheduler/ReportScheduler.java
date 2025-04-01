package com.dreisource.Scheduler;


import com.dreisource.erp_system.model.Report;
import com.dreisource.erp_system.repository.ReportRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReportScheduler {
    private final ReportRepository reportRepository;

    public ReportScheduler(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Scheduled(cron = "0 0 3 * * ?") // Runs every day at 3 AM
    @Transactional
    public void cleanUpOldReports() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(60); // Delete reports older than 60 days
        System.out.println("Scheduled task: Cleaning up reports older than " + thresholdDate);

        // Soft delete approach (mark as deleted instead of deleting)
        List<Report> oldReports = reportRepository.findByCreatedAtBeforeAndDeletedFalse(thresholdDate);
        for (Report report : oldReports) {
            report.setDeleted(true);
            reportRepository.save(report);
        }

        System.out.println("Scheduled task completed. Marked " + oldReports.size() + " reports as deleted.");
    }
}