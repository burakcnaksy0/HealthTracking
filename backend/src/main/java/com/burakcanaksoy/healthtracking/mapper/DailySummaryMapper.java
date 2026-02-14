package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.dto.DailySummary;
import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DailySummaryMapper {

    public DailySummary mapToDailySummary(List<MealLog> mealLogs, List<WorkoutLog> workoutLogs,
            List<WaterIntake> waterIntakes) {

        BigDecimal totalCaloriesIn = mealLogs.stream()
                .map(MealLog::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalProtein = mealLogs.stream()
                .map(MealLog::getProtein)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCarbs = mealLogs.stream()
                .map(MealLog::getCarbs)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFat = mealLogs.stream()
                .map(MealLog::getFat)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCaloriesOut = workoutLogs.stream()
                .map(WorkoutLog::getCaloriesBurned)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalDailyWorkout = workoutLogs.stream()
                .mapToInt(WorkoutLog::getDuration)
                .sum();


        int totalWater = waterIntakes.stream()
                .mapToInt(WaterIntake::getAmount)
                .sum();

        return DailySummary.builder()
                .totalCaloriesIn(totalCaloriesIn)
                .totalCaloriesOut(totalCaloriesOut)
                .netCalories(totalCaloriesIn.subtract(totalCaloriesOut))
                .totalProteinIn(totalProtein)
                .totalCarbIn(totalCarbs)
                .totalFatIn(totalFat)
                .totalWaterIntakeIn(totalWater)
                .totalDailyWorkout(totalDailyWorkout)
                .build();
    }
}
