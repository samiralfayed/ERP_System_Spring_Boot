package com.dreisource.erp_system.service;


import com.dreisource.erp_system.model.Analytics;

import java.util.List;
import java.util.Optional;

public interface AnalyticsService {
    Analytics saveAnalytics(Analytics analytics);
    List<Analytics> getAllAnalytics();
    Optional<Analytics> getAnalyticsById(Long id);
    Analytics updateAnalytics(Long id, Analytics analytics);
    void deleteAnalytics(Long id);
    List<Analytics> getAnalyticsByCategory(String category);
    List<Analytics> getAnalyticsBySource(String source);
}