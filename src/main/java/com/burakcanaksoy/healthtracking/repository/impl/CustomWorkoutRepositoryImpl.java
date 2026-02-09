package com.burakcanaksoy.healthtracking.repository.impl;

import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.repository.custom.CustomWorkoutRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomWorkoutRepositoryImpl implements CustomWorkoutRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomWorkoutRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WorkoutLog> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay,
            LocalDateTime endOfDay) {
        String sql = "SELECT * FROM workout_log WHERE user_id = ? AND created_at >= ? AND created_at < ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, workoutLogRowMapper, userId, startOfDay, endOfDay);

    }

    private final RowMapper<WorkoutLog> workoutLogRowMapper = (rs, rowNum) -> WorkoutLog.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .exerciseName(rs.getString("exercise_name"))
            .duration(rs.getInt("duration_minutes"))
            .caloriesBurned(rs.getBigDecimal("calories_burned"))
            .createdAt(rs.getObject("created_at", LocalDateTime.class))
            .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
            .build();
}
