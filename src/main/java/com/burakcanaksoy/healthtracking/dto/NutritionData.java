package com.burakcanaksoy.healthtracking.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class NutritionData {
    private BigDecimal caloriesPer100g;
    private BigDecimal proteinPer100g;
    private BigDecimal carbsPer100g;
    private BigDecimal fatPer100g;

    public static NutritionData empty() {
        return NutritionData.builder()
                .caloriesPer100g(BigDecimal.ZERO)
                .proteinPer100g(BigDecimal.ZERO)
                .carbsPer100g(BigDecimal.ZERO)
                .fatPer100g(BigDecimal.ZERO)
                .build();
    }
}
