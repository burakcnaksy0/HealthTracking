package com.burakcanaksoy.healthtracking.response;

import com.burakcanaksoy.healthtracking.model.enums.MealTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionResponse {
    private MealTime mealTime;
    private String foodName;
    private BigDecimal calories;
    private BigDecimal amount;
    private String unit;
    private BigDecimal protein;
    private BigDecimal carbs;
    private BigDecimal fat;
}
