package com.burakcanaksoy.healthtracking.repository.impl;

import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.enums.MealTime;
import com.burakcanaksoy.healthtracking.repository.custom.CustomNutritionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomNutritionRepositoryImpl implements CustomNutritionRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MealLog> mealLogRowMapper = (rs, rowNum) -> MealLog.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .mealTime(MealTime.valueOf(rs.getString("meal_time").toUpperCase()))
            .foodName(rs.getString("food_name"))
            .calories(rs.getBigDecimal("calories"))
            .amount(rs.getBigDecimal("amount"))
            .unit(rs.getString("unit"))
            .protein(rs.getBigDecimal("protein"))
            .carbs(rs.getBigDecimal("carbs"))
            .fat(rs.getBigDecimal("fat"))
            .createdAt(rs.getObject("created_at", LocalDateTime.class))
            .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
            .build();

    public CustomNutritionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MealLog> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay,
            LocalDateTime endOfDay) {
        String sql = "SELECT * FROM meal_log WHERE user_id = ? AND created_at >= ? AND created_at < ? ORDER BY created_at ASC";
        return jdbcTemplate.query(sql, mealLogRowMapper, userId, startOfDay, endOfDay);
    }

}
