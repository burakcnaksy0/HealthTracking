package com.burakcanaksoy.healthtracking.repository.custom;

import com.burakcanaksoy.healthtracking.model.MealLog;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomNutritionRepository {
    List<MealLog> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
