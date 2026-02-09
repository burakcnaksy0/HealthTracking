package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.request.WorkoutLogRequest;
import com.burakcanaksoy.healthtracking.response.WorkoutLogResponse;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class WorkoutLogMapper {

    public WorkoutLog toEntity(WorkoutLogRequest request) {
        return WorkoutLog.builder()
                .exerciseName(request.getExerciseName())
                .duration(request.getDuration())
                .build();
    }

    public WorkoutLogResponse toResponse(WorkoutLog workoutLog) {
        return WorkoutLogResponse.builder()
                .exerciseName(workoutLog.getExerciseName())
                .duration(workoutLog.getDuration())
                .caloriesBurned(workoutLog.getCaloriesBurned())
                .build();
    }

    public List<WorkoutLogResponse> toResponseList(List<WorkoutLog> workoutLogList){
        if (workoutLogList == null && workoutLogList.isEmpty()){
            return List.of();
        }
        return workoutLogList.stream()
                .map(this::toResponse)
                .toList();
    }
}
