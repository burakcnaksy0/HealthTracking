package com.burakcanaksoy.healthtracking.config;

import com.burakcanaksoy.healthtracking.job.DailyRecommendationJob;
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
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 23 59 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(dailyRecommendationJobDetail())
                .withIdentity("dailyRecommendationTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
