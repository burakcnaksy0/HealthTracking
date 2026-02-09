package com.burakcanaksoy.healthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailySummary {
    private BigDecimal totalCaloriesIn;
    private BigDecimal totalCaloriesOut;
    private BigDecimal netCalories;
    private BigDecimal totalProteinIn;
    private BigDecimal totalCarbIn;
    private BigDecimal totalFatIn;
    private int totalWaterIntakeIn;
    private int totalDailyWorkout;
}