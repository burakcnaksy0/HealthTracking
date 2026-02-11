package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.data.WeeklyStats;
import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.Reports;
import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.repository.NutritionRepository;
import com.burakcanaksoy.healthtracking.repository.WaterIntakeRepository;
import com.burakcanaksoy.healthtracking.repository.WorkoutLogRepository;
import com.burakcanaksoy.healthtracking.response.ReportsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeeklyReportMapper {

    private final NutritionRepository nutritionRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final WaterIntakeRepository waterIntakeRepository;

    public WeeklyStats collectAndCalculateWeeklyStats(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<MealLog> meals = nutritionRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        List<WorkoutLog> workouts = workoutLogRepository.findByUserIdAndCreatedAtBetween(userId, startDate,
                endDate);
        List<WaterIntake> waterIntakes = waterIntakeRepository.findByUserIdAndCreatedAtBetween(userId,
                startDate, endDate);

        return calculateWeeklyStats(meals, workouts, waterIntakes);
    }

    public WeeklyStats calculateWeeklyStats(List<MealLog> meals, List<WorkoutLog> workouts,
                                            List<WaterIntake> waterIntakes) {
        WeeklyStats stats = new WeeklyStats();

        stats.setTotalCaloriesIn(meals.stream()
                .map(MealLog::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        BigDecimal totalProtein = meals.stream()
                .map(MealLog::getProtein)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCarbs = meals.stream()
                .map(MealLog::getCarbs)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalFat = meals.stream()
                .map(MealLog::getFat)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        stats.setTotalCaloriesOut(workouts.stream()
                .map(WorkoutLog::getCaloriesBurned)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        stats.setTotalWorkoutMinutes(workouts.stream()
                .mapToInt(WorkoutLog::getDuration)
                .sum());

        int totalWater = waterIntakes.stream()
                .mapToInt(WaterIntake::getAmount)
                .sum();

        stats.setAvgProtein(divide(totalProtein, 7));
        stats.setAvgCarbs(divide(totalCarbs, 7));
        stats.setAvgFat(divide(totalFat, 7));
        stats.setAvgWater(BigDecimal.valueOf(totalWater).divide(
                BigDecimal.valueOf(7), 2, RoundingMode.HALF_UP));

        stats.setActiveDays((int) meals.stream()
                .map(m -> m.getCreatedAt().toLocalDate())
                .distinct()
                .count());

        stats.setWorkoutDays((int) workouts.stream()
                .map(w -> w.getCreatedAt().toLocalDate())
                .distinct()
                .count());

        return stats;
    }

    public Reports toReportsEntity(Long userId, WeeklyStats stats, String reportText) {
        return Reports.builder()
                .userId(userId)
                .reportText(reportText)
                .totalCaloriesIn(stats.getTotalCaloriesIn())
                .totalCaloriesOut(stats.getTotalCaloriesOut())
                .averageProtein(stats.getAvgProtein())
                .averageCarb(stats.getAvgCarbs())
                .averageFat(stats.getAvgFat())
                .averageWater(stats.getAvgWater())
                .generatedAt(LocalDateTime.now())
                .build();
    }

    private BigDecimal divide(BigDecimal value, int divisor) {
        if (divisor == 0) {
            return BigDecimal.ZERO;
        }
        return value.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP);
    }

    public ReportsResponse mapToResponse(Reports report) {
        ReportsResponse response = new ReportsResponse();
        response.setReportText(report.getReportText());
        response.setTotalCaloriesIn(report.getTotalCaloriesIn());
        response.setTotalCaloriesOut(report.getTotalCaloriesOut());
        response.setAverageProtein(report.getAverageProtein());
        response.setAverageCarb(report.getAverageCarb());
        response.setAverageFat(report.getAverageFat());
        response.setAverageWater(report.getAverageWater());
        return response;
    }

}
