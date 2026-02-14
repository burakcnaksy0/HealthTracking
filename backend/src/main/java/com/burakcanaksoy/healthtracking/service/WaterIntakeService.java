package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.mapper.WaterIntakeMapper;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.repository.WaterIntakeRepository;
import com.burakcanaksoy.healthtracking.request.WaterIntakeRequest;
import com.burakcanaksoy.healthtracking.response.WaterIntakeResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WaterIntakeService {
    private final WaterIntakeRepository waterIntakeRepository;
    private final WaterIntakeMapper waterIntakeMapper;

    public WaterIntakeService(WaterIntakeRepository waterIntakeRepository, WaterIntakeMapper waterIntakeMapper) {
        this.waterIntakeRepository = waterIntakeRepository;
        this.waterIntakeMapper = waterIntakeMapper;
    }

    public WaterIntakeResponse intakeWater(WaterIntakeRequest waterIntakeRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        WaterIntake waterIntake = waterIntakeMapper.toEntity(waterIntakeRequest);
        waterIntake.setUserId(user.getId());
        waterIntake.setCreatedAt(LocalDateTime.now());

        WaterIntake savedIntake = waterIntakeRepository.save(waterIntake);

        return waterIntakeMapper.toResponse(savedIntake);
    }

    public int getTodayTotalWater() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return waterIntakeRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay, endOfDay)
                .stream()
                .mapToInt(WaterIntake::getAmount)
                .sum();
    }
}
