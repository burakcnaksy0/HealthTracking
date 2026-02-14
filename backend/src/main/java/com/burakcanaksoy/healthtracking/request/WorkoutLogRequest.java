package com.burakcanaksoy.healthtracking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutLogRequest {
    private String exerciseName;
    private int duration;
}
