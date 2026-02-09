package com.burakcanaksoy.healthtracking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutLogResponse {
    private String exerciseName;
    private int duration;
    private BigDecimal caloriesBurned;
}
