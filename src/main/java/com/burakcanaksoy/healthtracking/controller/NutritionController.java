package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.request.NutritionRequest;
import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.response.NutritionResponse;
import com.burakcanaksoy.healthtracking.service.NutritionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {
    private final NutritionService nutritionService;

    public NutritionController(NutritionService nutritionService){
        this.nutritionService = nutritionService;
    }

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<NutritionResponse>> logNutrition(@Valid @RequestBody NutritionRequest request){
        return ResponseEntity.ok(ApiResponse.success("Nutrition added.",nutritionService.logNutrition(request)));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<NutritionResponse>>> dailyNutritionHistory(){
        List<NutritionResponse> response  = nutritionService.dailyNutritionHistory();
        return ResponseEntity.ok(ApiResponse.success("Daily Nutrition History",response));
    }
}
