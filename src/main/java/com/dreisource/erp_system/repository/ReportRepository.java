package com.dreisource.erp_system.repository;

import com.dreisource.erp_system.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCategory(String category);
    List<Report> findBySource(String source);
    List<Report> findByStatus(String status);
    List<Report> findByAuthor(String author);
    List<Report> findByCreatedAtBeforeAndDeletedFalse(LocalDateTime createdAt);
}