package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.config.NutritionConfig;
import com.burakcanaksoy.healthtracking.data.NutritionData;
import com.burakcanaksoy.healthtracking.mapper.NutritionMapper;
import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.NutritionRepository;
import com.burakcanaksoy.healthtracking.request.NutritionRequest;
import com.burakcanaksoy.healthtracking.response.NutritionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NutritionService {
    private final NutritionRepository nutritionRepository;
    private final RestTemplate restTemplate;
    private final NutritionMapper nutritionMapper;
    private final ObjectMapper objectMapper;
    private final NutritionConfig nutritionConfig;

    public NutritionResponse logNutrition(NutritionRequest request) {
        User user = getAuthenticatedUser();
        log.info("Authenticated user: {} ({})", user.getUsername(), user.getEmail());

        String url = UriComponentsBuilder.fromUriString(nutritionConfig.getBaseUrl())
                .queryParam("search_terms", request.getFoodName())
                .queryParam("search_simple", 1)
                .queryParam("action", "process")
                .queryParam("json", 1)
                .queryParam("page_size", 1)
                .queryParam("fields", "product_name,nutriments")
                .toUriString();

        try {
            String responseBody = restTemplate.getForObject(url, String.class);
            JsonNode apiResponse = objectMapper.readTree(responseBody);

            NutritionData nutritionData = nutritionMapper.extractNutritionData(apiResponse);

            MealLog mealLog = nutritionMapper.toMealLog(request, nutritionData, user);
            MealLog savedLog = nutritionRepository.save(mealLog);

            return nutritionMapper.toResponse(savedLog);

        } catch (Exception e) {
            log.error("Error processing nutrition data for food: {}", request.getFoodName(), e);
            throw new RuntimeException("Failed to process nutrition data", e);
        }
    }

    public List<NutritionResponse> dailyNutritionHistory() {
        User user = getAuthenticatedUser();

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<MealLog> mealLogs = nutritionRepository.findByUserIdAndCreatedAtBetween(
                user.getId(),
                startOfDay,
                endOfDay);

        return nutritionMapper.toResponseList(mealLogs);
    }

    private User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        throw new RuntimeException("Unauthorized access");
    }
}
