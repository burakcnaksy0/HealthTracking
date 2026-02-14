package com.burakcanaksoy.healthtracking.job;

import com.burakcanaksoy.healthtracking.service.RemainderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MiddayCheckJob implements Job {
    private final RemainderService remainderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("MiddayCheckJob trigger received.");
        remainderService.generateMidDayReminder();
    }

}
