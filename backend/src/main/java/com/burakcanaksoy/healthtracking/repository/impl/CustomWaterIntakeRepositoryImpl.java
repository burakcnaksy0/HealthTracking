package com.burakcanaksoy.healthtracking.repository.impl;

import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.repository.custom.CustomWaterIntakeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomWaterIntakeRepositoryImpl implements CustomWaterIntakeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<WaterIntake> waterIntakeRowMapper = (rs, rowNum) -> WaterIntake.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .amount(rs.getInt("amount_ml"))
            .createdAt(rs.getObject("created_at", LocalDateTime.class))
            .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
            .build();

    public CustomWaterIntakeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WaterIntake> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay,
            LocalDateTime endOfDay) {
        String sql = "SELECT * FROM water_intake WHERE user_id = ? AND created_at >= ? AND created_at < ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, waterIntakeRowMapper, userId, startOfDay, endOfDay);
    }
}
