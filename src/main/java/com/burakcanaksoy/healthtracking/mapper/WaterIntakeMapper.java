package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.request.WaterIntakeRequest;
import com.burakcanaksoy.healthtracking.response.WaterIntakeResponse;
import org.springframework.stereotype.Component;

@Component
public class WaterIntakeMapper {

    public WaterIntake toEntity(WaterIntakeRequest request){
        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setAmount(request.getAmount());
        return waterIntake;
    }

    public WaterIntakeResponse toResponse(WaterIntake waterIntake){
        WaterIntakeResponse response = new WaterIntakeResponse();
        response.setAmount(waterIntake.getAmount());
        return response;
    }
}
