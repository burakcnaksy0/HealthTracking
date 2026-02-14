package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.model.Reminder;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.ReminderRepository;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemainderService {
    private final PromptService promptService;
    private final LlmService llmService;
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public void generateMidDayReminder() {
        log.info("Midday remainder starting...");
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                Reminder result = processMidDayReminder(user);
                if (result != null) {
                    log.info("Generated Midday remainder for user: {}", user.getUsername());
                }
            } catch (Exception e) {
                log.error("Failed to check midday status for user: {}", user.getUsername(), e);
            }
        }
        log.info("Midday remainder completed.");
    }

    public Reminder createMidDayReminder() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return processMidDayReminder(user);
    }

    private Reminder processMidDayReminder(User user) {
        String prompt = promptService.createReminderPrompt(user);

        if (prompt == null) {
            log.info("User {} has already logged food/water. No midday reminder needed.", user.getUsername());
            return null;
        }

        String reminderText = llmService.getRecommendation(prompt);

        Reminder reminder = Reminder
                .builder()
                .userId(user.getId())
                .remainderText(reminderText)
                .generatedAt(java.time.LocalDateTime.now())
                .build();

        log.info("Generated midday reminder for user: {}", user.getUsername());
        return reminderRepository.save(reminder);
    }
}
