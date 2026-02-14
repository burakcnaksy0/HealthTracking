package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.dto.DailySummary;
import com.burakcanaksoy.healthtracking.mapper.DailySummaryMapper;
import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.repository.NutritionRepository;
import com.burakcanaksoy.healthtracking.repository.WaterIntakeRepository;
import com.burakcanaksoy.healthtracking.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailySummaryService {
    private final NutritionRepository nutritionRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final WaterIntakeRepository waterIntakeRepository;
    private final DailySummaryMapper dailySummaryMapper;

    public DailySummary generateDailySummary(User user) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<MealLog> mealLogs = nutritionRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay,
                endOfDay);
        List<WorkoutLog> workoutLogs = workoutLogRepository.findByUserIdAndCreatedAtBetween(user.getId(),
                startOfDay,
                endOfDay);
        List<WaterIntake> waterIntakes = waterIntakeRepository.findByUserIdAndCreatedAtBetween(user.getId(),
                startOfDay,
                endOfDay);

        return dailySummaryMapper.mapToDailySummary(mealLogs, workoutLogs, waterIntakes);
    }
}
