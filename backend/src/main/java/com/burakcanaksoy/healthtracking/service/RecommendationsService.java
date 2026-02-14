package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.model.Recommendations;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.RecommendationsRepository;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationsService {

    private final RecommendationsRepository recommendationsRepository;
    private final UserRepository userRepository;
    private final PromptService promptService;
    private final LlmService llmService;

    public void generateDailyRecommendationsForAllUsers() {
        log.info("Starting batch generation of daily recommendations...");
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                processUserRecommendation(user);
                log.info("Generated daily recommendation for user: {}", user.getUsername());
            } catch (Exception e) {
                log.error("Failed to generate recommendation for user: {}", user.getUsername(), e);
            }
        }
        log.info("Batch generation completed.");
    }

    /**
     * Finds the latest daily recommendation for the authenticated user.
     * If none exists, it can either return null or empty optional, or create one
     * (optional).
     * Based on requirements, we should return the latest one.
     */
    public Recommendations getLatestDailyRecommendation() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recommendationsRepository.findTopByUserIdOrderByGeneratedAtDesc(user.getId())
                .orElse(null);
    }

    // Kept for backward compatibility if needed, but discouraged
    public Recommendations generateDailyRecommendationForCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return processUserRecommendation(user);
    }

    private Recommendations processUserRecommendation(User user) {
        String prompt = promptService.createCoachingPrompt(user);
        String recommendationText = llmService.getRecommendation(prompt);

        Recommendations recommendation = Recommendations.builder()
                .userId(user.getId())
                .recommendationText(recommendationText)
                .generatedAt(LocalDateTime.now())
                .build();

        return recommendationsRepository.save(recommendation);
    }
}