package com.burakcanaksoy.healthtracking.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WaterIntakeRequest {
    @Min(value = 1, message = "Water amount must be greater than 0")
    private int amount;

}
