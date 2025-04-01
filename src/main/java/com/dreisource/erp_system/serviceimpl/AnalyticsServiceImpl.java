package com.dreisource.erp_system.serviceimpl;



import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.repository.AnalyticsRepository;
import com.dreisource.erp_system.service.AnalyticsService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsServiceImpl(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public Analytics saveAnalytics(Analytics analytics) {
        return analyticsRepository.save(analytics);
    }

    @Override
    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }

    @Override
    public Optional<Analytics> getAnalyticsById(Long id) {
        return analyticsRepository.findById(id);
    }

    @Override
    public Analytics updateAnalytics(Long id, Analytics analytics) {
        analytics.setId(id);
        return analyticsRepository.save(analytics);
    }

    @Override
    public void deleteAnalytics(Long id) {
        analyticsRepository.deleteById(id);
    }

    @Override
    public List<Analytics> getAnalyticsByCategory(String category) {
        return analyticsRepository.findByCategory(category);
    }

    @Override
    public List<Analytics> getAnalyticsBySource(String source) {
        return analyticsRepository.findBySource(source);
    }
}
