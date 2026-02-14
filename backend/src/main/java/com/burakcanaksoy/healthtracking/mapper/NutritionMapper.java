package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.data.NutritionData;
import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.model.enums.MealTime;
import com.burakcanaksoy.healthtracking.request.NutritionRequest;
import com.burakcanaksoy.healthtracking.response.NutritionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@Slf4j
public class NutritionMapper {

    public NutritionData extractNutritionData(JsonNode apiResponse) {
        JsonNode products = apiResponse.path("products");

        if (!products.isArray() || products.isEmpty()) {
            log.warn("No products found in API response");
            return NutritionData.empty();
        }

        JsonNode nutriments = products.get(0).path("nutriments");

        return NutritionData.builder()
                .caloriesPer100g(getBigDecimal(nutriments, "energy-kcal_100g"))
                .proteinPer100g(getBigDecimal(nutriments, "proteins_100g"))
                .carbsPer100g(getBigDecimal(nutriments, "carbohydrates_100g"))
                .fatPer100g(getBigDecimal(nutriments, "fat_100g"))
                .build();
    }

    public MealLog toMealLog(NutritionRequest request, NutritionData nutritionData, User user) {
        BigDecimal multiplier = request.getAmount().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        return MealLog.builder()
                .userId(user.getId())
                .mealTime(MealTime.valueOf(request.getMealTime()))
                .foodName(request.getFoodName())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .calories(nutritionData.getCaloriesPer100g().multiply(multiplier))
                .protein(nutritionData.getProteinPer100g().multiply(multiplier))
                .carbs(nutritionData.getCarbsPer100g().multiply(multiplier))
                .fat(nutritionData.getFatPer100g().multiply(multiplier))
                .build();
    }

    public NutritionResponse toResponse(MealLog mealLog) {
        return NutritionResponse.builder()
                .id(mealLog.getId())
                .mealTime(mealLog.getMealTime())
                .foodName(mealLog.getFoodName())
                .amount(mealLog.getAmount())
                .unit(mealLog.getUnit())
                .calories(mealLog.getCalories())
                .protein(mealLog.getProtein())
                .carbs(mealLog.getCarbs())
                .fat(mealLog.getFat())
                .createdAt(mealLog.getCreatedAt() != null ? mealLog.getCreatedAt().toString() : null)
                .build();
    }

    public List<NutritionResponse> toResponseList(List<MealLog> mealLogs) {
        if (mealLogs == null || mealLogs.isEmpty()) {
            return List.of();
        }
        return mealLogs.stream()
                .map(this::toResponse)
                .toList();
    }

    private BigDecimal getBigDecimal(JsonNode node, String fieldName) {
        if (node.has(fieldName) && !node.get(fieldName).isNull()) {
            try {
                return new BigDecimal(node.get(fieldName).asText());
            } catch (NumberFormatException e) {
                log.warn("Failed to parse {} as BigDecimal", fieldName);
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }
}
