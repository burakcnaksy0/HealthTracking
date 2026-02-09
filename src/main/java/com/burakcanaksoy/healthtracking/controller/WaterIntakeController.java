package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.request.WaterIntakeRequest;
import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.response.WaterIntakeResponse;
import com.burakcanaksoy.healthtracking.service.WaterIntakeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/water")
public class WaterIntakeController {
    private final WaterIntakeService waterIntakeService;

    public WaterIntakeController(WaterIntakeService waterIntakeService){
        this.waterIntakeService = waterIntakeService;
    }

    @PostMapping("/intake")
    public ResponseEntity<ApiResponse<WaterIntakeResponse>> intakeWater(@Valid @RequestBody WaterIntakeRequest waterIntakeRequest){
        WaterIntakeResponse response = waterIntakeService.intakeWater(waterIntakeRequest);
        return ResponseEntity.ok(ApiResponse.success("Water intake successful.",response));
    }

}
