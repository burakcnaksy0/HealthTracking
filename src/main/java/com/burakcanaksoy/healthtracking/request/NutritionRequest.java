package com.burakcanaksoy.healthtracking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionRequest {
    private String mealTime;
    private String foodName;
    private BigDecimal amount;
    private String unit;
}
