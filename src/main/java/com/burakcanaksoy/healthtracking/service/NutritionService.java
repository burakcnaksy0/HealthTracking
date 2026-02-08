package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.dto.NutritionData;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NutritionService {
    private final NutritionRepository nutritionRepository;
    private final WebClient webClient;
    private final NutritionMapper nutritionMapper;
    private final ObjectMapper objectMapper;

    public NutritionResponse logNutrition(NutritionRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Authenticated user: {} ({})", user.getUsername(), user.getEmail());

        String responseBody = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi/search.pl")
                        .queryParam("search_terms", request.getFoodName())
                        .queryParam("search_simple", "1")
                        .queryParam("action", "process")
                        .queryParam("json", "1")
                        .queryParam("page_size", "1") // Limit to 1 result
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            // Parse API response
            JsonNode apiResponse = objectMapper.readTree(responseBody);

            // Extract nutrition data using mapper
            NutritionData nutritionData = nutritionMapper.extractNutritionData(apiResponse);

            // Map to entity and save
            MealLog mealLog = nutritionMapper.toMealLog(request, nutritionData, user);
            MealLog savedLog = nutritionRepository.save(mealLog);

            // Map to response
            return nutritionMapper.toResponse(savedLog);

        } catch (Exception e) {
            log.error("Error processing nutrition data for food: {}", request.getFoodName(), e);
            throw new RuntimeException("Failed to process nutrition data", e);
        }
    }

    public List<NutritionResponse> dailyNutritionHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<MealLog> mealLogs = nutritionRepository.findByUserIdAndCreatedAtBetween(
                user.getId(),
                startOfDay,
                endOfDay);

        return nutritionMapper.toResponseList(mealLogs);
    }
}
