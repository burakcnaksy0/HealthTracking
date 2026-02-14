package com.burakcanaksoy.healthtracking.config;

import com.burakcanaksoy.healthtracking.job.DailyRecommendationJob;
import com.burakcanaksoy.healthtracking.job.MiddayCheckJob;
import com.burakcanaksoy.healthtracking.job.WeeklyReportJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final JobConfig jobConfig;

    @Bean
    public JobDetail dailyRecommendationJobDetail() {
        return JobBuilder.newJob(DailyRecommendationJob.class)
                .withIdentity("dailyRecommendationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyRecommendationJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig.getDailyRecommendation());

        return TriggerBuilder.newTrigger()
                .forJob(dailyRecommendationJobDetail())
                .withIdentity("dailyRecommendationTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail middayCheckJobDetail() {
        return JobBuilder.newJob(MiddayCheckJob.class)
                .withIdentity("middayCheckJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger middayCheckJobDetailTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig.getMidDayReminder());

        return TriggerBuilder.newTrigger()
                .forJob(middayCheckJobDetail())
                .withIdentity("middayCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail weeklyReportsJobDetail() {
        return JobBuilder.newJob(WeeklyReportJob.class)
                .withIdentity("weeklyReportsJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger weeklyReportsJobDetailTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig.getWeeklyReports());

        return TriggerBuilder.newTrigger()
                .forJob(weeklyReportsJobDetail())
                .withIdentity("weeklyReportsTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
