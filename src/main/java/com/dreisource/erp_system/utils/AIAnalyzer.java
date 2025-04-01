package com.dreisource.erp_system.utils;


import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.model.Report;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AIAnalyzer {
    // Analyze most common trends in Analytics
    public String analyzeAnalyticsTrends(List<Analytics> analyticsList) {
        if (analyticsList.isEmpty()) return "No analytics data available for trend analysis.";

        Map<String, Long> categoryCount = analyticsList.stream()
                .collect(Collectors.groupingBy(Analytics::getCategory, Collectors.counting()));

        String mostCommonCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Data");

        return "AI Analysis: The most common analytics category is '" + mostCommonCategory + "' with "
                + categoryCount.get(mostCommonCategory) + " records.";
    }

    // Analyze most common trends in Reports
    public String analyzeReportTrends(List<Report> reports) {
        if (reports.isEmpty()) return "No reports available for trend analysis.";

        Map<String, Long> categoryCount = reports.stream()
                .collect(Collectors.groupingBy(Report::getCategory, Collectors.counting()));

        String mostCommonCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Data");

        return "AI Analysis: The most common report category is '" + mostCommonCategory + "' with "
                + categoryCount.get(mostCommonCategory) + " reports.";
    }

    // Detect anomalies in Analytics data
    public String detectAnalyticsAnomalies(List<Analytics> analyticsList) {
        if (analyticsList.isEmpty()) return "No analytics data available for anomaly detection.";

        long totalRecords = analyticsList.size();
        long criticalCount = analyticsList.stream()
                .filter(a -> a.getTitle().toLowerCase().contains("critical"))
                .count();

        double percentage = ((double) criticalCount / totalRecords) * 100;
        return percentage > 15 ? "🚨 ALERT: " + percentage + "% of analytics data contains critical issues!"
                : "✅ No significant anomalies detected in analytics.";
    }

    // Detect anomalies in Reports
    public String detectReportAnomalies(List<Report> reports) {
        if (reports.isEmpty()) return "No reports available for anomaly detection.";

        long totalReports = reports.size();
        long urgentReports = reports.stream()
                .filter(report -> report.getTitle().toLowerCase().contains("urgent"))
                .count();

        double percentage = ((double) urgentReports / totalReports) * 100;
        return percentage > 20 ? "🚨 ALERT: " + percentage + "% of reports contain urgent matters!"
                : "✅ No significant anomalies detected in reports.";
    }

    // Sentiment Analysis for Analytics
    public String analyzeAnalyticsSentiment(List<Analytics> analyticsList) {
        if (analyticsList.isEmpty()) return "No analytics data available for sentiment analysis.";

        long positiveCount = analyticsList.stream()
                .filter(a -> containsPositiveWords(a.getTitle()))
                .count();

        long negativeCount = analyticsList.stream()
                .filter(a -> containsNegativeWords(a.getTitle()))
                .count();

        return generateSentimentSummary(positiveCount, negativeCount, analyticsList.size(), "Analytics");
    }

    // Sentiment Analysis for Reports
    public String analyzeReportSentiment(List<Report> reports) {
        if (reports.isEmpty()) return "No reports available for sentiment analysis.";

        long positiveCount = reports.stream()
                .filter(r -> containsPositiveWords(r.getTitle()))
                .count();

        long negativeCount = reports.stream()
                .filter(r -> containsNegativeWords(r.getTitle()))
                .count();

        return generateSentimentSummary(positiveCount, negativeCount, reports.size(), "Reports");
    }

    // Predictive Analysis (Forecasting trends)
    public String predictTrends(List<Report> reports, List<Analytics> analyticsList) {
        if (reports.isEmpty() && analyticsList.isEmpty()) return "No data available for predictive analysis.";

        int reportGrowth = reports.size() / 30;  // Assuming 30 days of data
        int analyticsGrowth = analyticsList.size() / 30;  // Assuming 30 days of data

        return "📊 Predictive Analysis: Based on current trends, reports may grow by "
                + (reportGrowth * 12) + " per year, and analytics data by " + (analyticsGrowth * 12) + " per year.";
    }

    // Utility Methods
    private boolean containsPositiveWords(String text) {
        String[] positiveWords = {"excellent", "great", "positive", "success", "improved", "growth"};
        return Arrays.stream(positiveWords).anyMatch(text.toLowerCase()::contains);
    }

    private boolean containsNegativeWords(String text) {
        String[] negativeWords = {"critical", "failure", "decline", "issue", "urgent", "problem"};
        return Arrays.stream(negativeWords).anyMatch(text.toLowerCase()::contains);
    }

    private String generateSentimentSummary(long positiveCount, long negativeCount, long total, String dataType) {
        double positivePercentage = ((double) positiveCount / total) * 100;
        double negativePercentage = ((double) negativeCount / total) * 100;

        return "🧠 AI Sentiment Analysis for " + dataType + ": " + positivePercentage + "% Positive, "
                + negativePercentage + "% Negative.";
    }
}
