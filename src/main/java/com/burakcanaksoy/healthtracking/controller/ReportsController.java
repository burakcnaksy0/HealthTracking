package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.response.ReportsResponse;
import com.burakcanaksoy.healthtracking.service.WeeklyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportsController {

    private final WeeklyReportService weeklyReportService;

    @GetMapping("/weekly")
    public ResponseEntity<ApiResponse<ReportsResponse>> getWeeklySummaryReport() {
        ReportsResponse response = weeklyReportService.generateWeeklyReport();
        return ResponseEntity.ok(ApiResponse.success("The weekly summary report has been delivered.", response));
    }
}