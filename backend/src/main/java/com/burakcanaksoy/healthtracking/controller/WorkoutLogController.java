package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.request.WorkoutLogRequest;
import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.response.WorkoutLogResponse;
import com.burakcanaksoy.healthtracking.service.WorkoutLogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/workout")
public class WorkoutLogController {
    private final WorkoutLogService workoutLogService;

    public WorkoutLogController(WorkoutLogService workoutLogService){
        this.workoutLogService = workoutLogService;
    }

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<WorkoutLogResponse>> logWorkout(@Valid @RequestBody WorkoutLogRequest request){
        WorkoutLogResponse response = workoutLogService.logWorkout(request);
        return ResponseEntity.ok(ApiResponse.success("Workout added",response));

    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<WorkoutLogResponse>>> historyWorkout(){
        List<WorkoutLogResponse> responseList = workoutLogService.historyWorkout();
        return ResponseEntity.ok(ApiResponse.success("Daily workout history",responseList));

    }
}
