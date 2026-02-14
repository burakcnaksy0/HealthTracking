package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.model.Recommendations;
import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.service.RecommendationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<Recommendations>> createDailyRecommendation() {
        Recommendations recommendations = recommendationsService.getLatestDailyRecommendation();
        return ResponseEntity.ok(ApiResponse.success("Recommendations successfully retrieved.", recommendations));
    }

}
