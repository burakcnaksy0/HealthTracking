package com.burakcanaksoy.healthtracking.job;

import com.burakcanaksoy.healthtracking.service.WeeklyReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeeklyReportJob implements Job {

    private final WeeklyReportService weeklyReportService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("WeeklyReportJob triggered - Starting weekly report generation for all users");
        weeklyReportService.generateWeeklyReportsForAllUsers();
        log.info("WeeklyReportJob execution signal sent.");
    }
}
