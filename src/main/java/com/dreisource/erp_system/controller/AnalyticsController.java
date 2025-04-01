package com.dreisource.erp_system.controller;



import com.dreisource.erp_system.model.Analytics;
import com.dreisource.erp_system.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping
    public Analytics createAnalytics(@RequestBody Analytics analytics) {
        return analyticsService.saveAnalytics(analytics);
    }

    @GetMapping
    public List<Analytics> getAllAnalytics() {
        return analyticsService.getAllAnalytics();
    }

    @GetMapping("/{id}")
    public Optional<Analytics> getAnalyticsById(@PathVariable Long id) {
        return analyticsService.getAnalyticsById(id);
    }

    @PutMapping("/{id}")
    public Analytics updateAnalytics(@PathVariable Long id, @RequestBody Analytics analytics) {
        return analyticsService.updateAnalytics(id, analytics);
    }

    @DeleteMapping("/{id}")
    public void deleteAnalytics(@PathVariable Long id) {
        analyticsService.deleteAnalytics(id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Analytics>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(analyticsService.getAnalyticsByCategory(category));
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<Analytics>> getBySource(@PathVariable String source) {
        return ResponseEntity.ok(analyticsService.getAnalyticsBySource(source));
    }
}