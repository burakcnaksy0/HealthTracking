package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.data.WeeklyStats;
import com.burakcanaksoy.healthtracking.mapper.WeeklyReportMapper;
import com.burakcanaksoy.healthtracking.model.Reports;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.ReportsRepository;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import com.burakcanaksoy.healthtracking.response.ReportsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.burakcanaksoy.healthtracking.produce.ReportMessage;
import com.burakcanaksoy.healthtracking.produce.ReportProducer;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WeeklyReportService {
    private final ReportsRepository reportsRepository;
    private final UserRepository userRepository;
    private final WeeklyReportMapper weeklyReportMapper;
    private final PromptService promptService;
    private final LlmService llmService;
    private final ReportProducer reportProducer;

    public ReportsResponse generateWeeklyReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reports report = generateWeeklyReportForUser(user);
        return weeklyReportMapper.mapToResponse(report);
    }

    @Transactional
    public void generateWeeklyReportsForAllUsers() {
        log.info("Starting batch generation of weekly reports...");
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                generateWeeklyReportForUser(user);
                log.info("Generated weekly report for user: {}", user.getUsername());
            } catch (Exception e) {
                log.error("Failed to generate weekly report for user: {}", user.getUsername(), e);
            }
        }
        log.info("Batch generation completed.");
    }

    public Reports generateWeeklyReportForUser(User user) {
        log.info("Generating weekly report for user: {}", user.getUsername());

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(7);

        WeeklyStats stats = weeklyReportMapper.collectAndCalculateWeeklyStats(
                user.getId(), startDate, endDate);

        String prompt = promptService.generateReportText(user, stats, startDate, endDate);

        String reportText = llmService.getRecommendation(prompt);

        Reports report = weeklyReportMapper.toReportsEntity(user.getId(), stats, reportText);
        Reports savedReport = reportsRepository.save(report);

        log.info("Weekly report saved with ID: {}", savedReport.getId());

        try {
            ReportMessage message = new ReportMessage();
            message.setReportId(savedReport.getId());
            message.setEmail(user.getEmail());
            message.setReportText(savedReport.getReportText());
            reportProducer.sendReport(message);
            log.info("Report message sent to queue for user: {}", user.getUsername());
        } catch (Exception e) {
            log.error("Failed to send report message to queue for user: {}", user.getUsername(), e);
        }

        return savedReport;
    }
}