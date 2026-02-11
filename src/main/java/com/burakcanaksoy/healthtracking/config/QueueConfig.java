package com.burakcanaksoy.healthtracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("queue-config")
@Data
public class QueueConfig {
    private String reportQueue;
}
