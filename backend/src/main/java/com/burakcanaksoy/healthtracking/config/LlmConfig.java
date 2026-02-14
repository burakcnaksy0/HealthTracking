package com.burakcanaksoy.healthtracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("llm-config")
@Data
public class LlmConfig {
    private String apiKey;
    private String model;
    private String apiUrl;
}
