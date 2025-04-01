package com.dreisource.erp_system.graphql;

import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.repository.AnalyticsRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsMutationResolver implements GraphQLMutationResolver {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsMutationResolver(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public Analytics addAnalytics(String title, String event, String user, String category, double value) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // Creating analytics instance using the simplified constructor
        Analytics analytics = new Analytics(title, event, user, category, value);

        return analyticsRepository.save(analytics);
    }
}