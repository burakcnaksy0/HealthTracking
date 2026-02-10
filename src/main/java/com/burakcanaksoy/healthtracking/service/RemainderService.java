package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.model.Reminder;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.ReminderRepository;
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


    public Reminder createMidDayReminder(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        return createMidDayReminder(user);
    }
    public Reminder createMidDayReminder(User user){
        String prompt = promptService.createReminderPrompt(user);
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
