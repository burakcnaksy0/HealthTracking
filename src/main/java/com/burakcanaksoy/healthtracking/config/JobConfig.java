package com.burakcanaksoy.healthtracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("job-config")
@Data
public class JobConfig {
    private String dailyRecommendation;
    private String midDayReminder;
    private String weeklyReports;
}
