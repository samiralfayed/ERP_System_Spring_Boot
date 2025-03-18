package com.dreisource.erp_system.repository;

import com.dreisource.erp_system.model.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {}
