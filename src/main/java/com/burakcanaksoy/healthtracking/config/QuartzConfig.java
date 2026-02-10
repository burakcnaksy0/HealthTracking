package com.burakcanaksoy.healthtracking.config;

import com.burakcanaksoy.healthtracking.job.DailyRecommendationJob;
import com.burakcanaksoy.healthtracking.job.MiddayCheckJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail dailyRecommendationJobDetail() {
        return JobBuilder.newJob(DailyRecommendationJob.class)
                .withIdentity("dailyRecommendationJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyRecommendationJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 59 23 * * ?");

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
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 12 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(middayCheckJobDetail())
                .withIdentity("middayCheckTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
