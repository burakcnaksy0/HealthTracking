package com.burakcanaksoy.healthtracking.repository.custom;

import com.burakcanaksoy.healthtracking.model.WaterIntake;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomWaterIntakeRepository {
    List<WaterIntake> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
