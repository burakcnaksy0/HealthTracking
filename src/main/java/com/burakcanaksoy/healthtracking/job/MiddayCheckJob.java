package com.burakcanaksoy.healthtracking.job;

import com.burakcanaksoy.healthtracking.model.Reminder;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.ReminderRepository;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import com.burakcanaksoy.healthtracking.service.DailySummaryService;
import com.burakcanaksoy.healthtracking.service.LlmService;
import com.burakcanaksoy.healthtracking.service.RecommendationsService;
import com.burakcanaksoy.healthtracking.service.RemainderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class MiddayCheckJob implements Job {
    private final RemainderService remainderService;
    private final UserRepository userRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Midday remainder starting...");
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                remainderService.createMidDayReminder(user);
            } catch (Exception e) {
                log.error("Failed to check midday status for user: {}", user.getUsername(), e);
            }
        }
        log.info("Midday remainder completed.");
    }

}
