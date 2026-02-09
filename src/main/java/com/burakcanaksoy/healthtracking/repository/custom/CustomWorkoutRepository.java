package com.burakcanaksoy.healthtracking.repository.custom;

import com.burakcanaksoy.healthtracking.model.WorkoutLog;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomWorkoutRepository {
    List<WorkoutLog> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
