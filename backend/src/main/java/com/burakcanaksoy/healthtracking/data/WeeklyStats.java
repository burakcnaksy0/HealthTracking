package com.burakcanaksoy.healthtracking.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeeklyStats {
    private BigDecimal totalCaloriesIn = BigDecimal.ZERO;
    private BigDecimal totalCaloriesOut = BigDecimal.ZERO;
    private BigDecimal avgProtein = BigDecimal.ZERO;
    private BigDecimal avgCarbs = BigDecimal.ZERO;
    private BigDecimal avgFat = BigDecimal.ZERO;
    private BigDecimal avgWater = BigDecimal.ZERO;
    private int totalWorkoutMinutes = 0;
    private int activeDays = 0;
    private int workoutDays = 0;
}
