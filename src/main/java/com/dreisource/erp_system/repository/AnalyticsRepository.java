package com.dreisource.erp_system.repository;



import com.dreisource.erp_system.model.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    List<Analytics> findByCategory(String category);
    List<Analytics> findBySource(String source);
    List<Analytics> findByTimestampBeforeAndDeletedFalse(LocalDateTime timestamp);
}