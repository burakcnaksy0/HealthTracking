package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.dto.DailySummary;
import com.burakcanaksoy.healthtracking.mapper.DailySummaryMapper;
import com.burakcanaksoy.healthtracking.model.*;
import com.burakcanaksoy.healthtracking.repository.NutritionRepository;
import com.burakcanaksoy.healthtracking.repository.RecommendationsRepository;
import com.burakcanaksoy.healthtracking.repository.WaterIntakeRepository;
import com.burakcanaksoy.healthtracking.repository.WorkoutLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationsService {
    private final NutritionRepository nutritionRepository;
    private final WorkoutLogRepository workoutLogRepository;
    private final WaterIntakeRepository waterIntakeRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final DailySummaryMapper dailySummaryMapper;
    private final LlmService llmService;

    public Recommendations createDailyRecommendation() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return createDailyRecommendation(user);
    }

    public Recommendations createDailyRecommendation(User user) {
        DailySummary dailySummary = generateDailySummary(user);

        String prompt = String.format(
                "You are a nutrition coach. User Profile -> Age: %d, Weight: %s, Goal: %s. " +
                        "Daily Summary -> Calories In: %s, Calories Out: %s, Protein: %s, Carbs: %s, Fat: %s, Water: %d ml, Workout Duration: %d min. "
                        +
                        "Please provide 3 short, motivating, and corrective recommendations for this user based on their daily activity and goal.",
                user.getAge(), user.getWeight(), user.getGoal(),
                dailySummary.getTotalCaloriesIn(), dailySummary.getTotalCaloriesOut(), dailySummary.getTotalProteinIn(),
                dailySummary.getTotalCarbIn(), dailySummary.getTotalFatIn(), dailySummary.getTotalWaterIntakeIn(),
                dailySummary.getTotalDailyWorkout());

        String recommendationText = llmService.getRecommendation(prompt);

        Recommendations recommendation = Recommendations
                .builder()
                .userId(user.getId())
                .recommendationText(recommendationText)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationsRepository.save(recommendation);
    }

    public DailySummary generateDailySummary(User user) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<MealLog> mealLogs = nutritionRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay,
                endOfDay);
        List<WorkoutLog> workoutLogs = workoutLogRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay,
                endOfDay);
        List<WaterIntake> waterIntakes = waterIntakeRepository.findByUserIdAndCreatedAtBetween(user.getId(), startOfDay,
                endOfDay);

        return dailySummaryMapper.mapToDailySummary(mealLogs, workoutLogs, waterIntakes);
    }
}
