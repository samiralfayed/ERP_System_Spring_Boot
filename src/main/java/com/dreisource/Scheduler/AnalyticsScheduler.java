package com.dreisource.Scheduler;



import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.repository.AnalyticsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AnalyticsScheduler {
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsScheduler(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?") // Runs every day at 2 AM
    @Transactional
    public void cleanUpOldAnalytics() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(90); // Delete analytics older than 90 days
        System.out.println("Scheduled task: Cleaning up analytics older than " + thresholdDate);

        // Soft delete approach (mark as deleted instead of deleting)
        List<Analytics> oldAnalytics = analyticsRepository.findByTimestampBeforeAndDeletedFalse(thresholdDate);
        for (Analytics analytics : oldAnalytics) {
            analytics.setDeleted(true);
            analyticsRepository.save(analytics);
        }

        System.out.println("Scheduled task completed. Marked " + oldAnalytics.size() + " analytics records as deleted.");
    }
}