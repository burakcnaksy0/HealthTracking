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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationsService {
        private final RecommendationsRepository recommendationsRepository;
        private final WorkoutLogService workoutLogService;
        private final LlmService llmService;
        private final PromptService promptService;
        private final DailySummaryService dailySummaryService;

        public Recommendations createDailyRecommendation() {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return createDailyRecommendation(user);
        }

        public Recommendations createDailyRecommendation(User user) {
                DailySummary dailySummary = dailySummaryService.generateDailySummary(user);
                BigDecimal bmr = workoutLogService.calculateBMH(user);

                String prompt = promptService.createCoachingPrompt(user, dailySummary, bmr);

                String recommendationText = llmService.getRecommendation(prompt);

                Recommendations recommendation = Recommendations
                                .builder()
                                .userId(user.getId())
                                .recommendationText(recommendationText)
                                .generatedAt(LocalDateTime.now())
                                .build();

                return recommendationsRepository.save(recommendation);
        }
}