package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.MealLog;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NutritionRepository extends CrudRepository<MealLog, Long> {

    @Query("SELECT * FROM meal_log WHERE user_id = :userId AND created_at >= :startOfDay AND created_at < :endOfDay ORDER BY created_at DESC")
    List<MealLog> findByUserIdAndCreatedAtBetween(
            @Param("userId") Long userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
