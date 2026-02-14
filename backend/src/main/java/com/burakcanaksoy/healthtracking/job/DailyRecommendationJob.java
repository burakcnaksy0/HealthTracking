package com.burakcanaksoy.healthtracking.job;

import com.burakcanaksoy.healthtracking.service.RecommendationsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailyRecommendationJob implements Job {

    private final RecommendationsService recommendationsService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("DailyRecommendationJob trigger received.");
        recommendationsService.generateDailyRecommendationsForAllUsers();
    }
}
