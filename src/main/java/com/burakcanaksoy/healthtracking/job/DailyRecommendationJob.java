package com.burakcanaksoy.healthtracking.job;

import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import com.burakcanaksoy.healthtracking.service.RecommendationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyRecommendationJob implements Job {

    private final UserRepository userRepository;
    private final RecommendationsService recommendationsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Starting DailyRecommendationJob...");
        // 1. Fetch all users
        Iterable<User> users = userRepository.findAll();

        // 2. Iterate and generate recommendation for each
        for (User user : users) {
            try {
                recommendationsService.createDailyRecommendation(user);
                log.info("Generated daily recommendation for user: {}", user.getUsername());
            } catch (Exception e) {
                log.error("Failed to generate recommendation for user: {}", user.getUsername(), e);
            }
        }
        log.info("DailyRecommendationJob completed.");
    }
}
