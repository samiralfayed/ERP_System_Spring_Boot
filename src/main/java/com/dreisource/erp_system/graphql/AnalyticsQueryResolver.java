package com.dreisource.erp_system.graphql;

import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.repository.AnalyticsRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AnalyticsQueryResolver implements GraphQLQueryResolver {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsQueryResolver(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public List<Analytics> getAnalyticsByCategory(String category) {
        List<Analytics> analyticsList = analyticsRepository.findByCategory(category);
        if (analyticsList.isEmpty()) {
            throw new RuntimeException("No analytics found for category: " + category);
        }
        return analyticsList;
    }
}