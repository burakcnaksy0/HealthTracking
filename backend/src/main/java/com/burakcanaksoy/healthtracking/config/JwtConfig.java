package com.burakcanaksoy.healthtracking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt-config")
@Data
public class JwtConfig {
    private String secretKey;
    private long jwtExpiration;
}
