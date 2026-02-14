package com.burakcanaksoy.healthtracking.model;

import com.burakcanaksoy.healthtracking.model.enums.MealTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("meal_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealLog {
    @Id
    private Long id;

    private Long userId;

    private MealTime mealTime;
    private String foodName;
    private BigDecimal calories;
    private BigDecimal amount;
    private String unit;
    private BigDecimal protein;
    private BigDecimal carbs;
    private BigDecimal fat;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
