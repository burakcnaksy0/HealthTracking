package com.burakcanaksoy.healthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklySummary {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;

    private BigDecimal totalCaloriesIn;
    private BigDecimal totalCaloriesOut;
    private BigDecimal totalProteinIn;
    private BigDecimal totalCarbIn;
    private BigDecimal totalFatIn;
    private int totalWaterIntakeIn;
    private int totalWorkoutMinutes;

    private BigDecimal averageCaloriesIn;
    private BigDecimal averageCaloriesOut;
    private BigDecimal averageProteinIn;
    private BigDecimal averageCarbIn;
    private BigDecimal averageFatIn;
    private double averageWaterIntakeIn;
    private double averageWorkoutMinutes;

    private List<DailySummary> dailySummaries;

    private List<String> dailyRecommendations;

    private int activeDays;
    private int workoutDays;
}
