package com.burakcanaksoy.healthtracking.config;

import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {
    private final QueueConfig queueConfig;

    @Bean
    public Queue reportQueue() {
        return new ActiveMQQueue(queueConfig.getReportQueue());
    }

    @Bean
    public MessageConverter converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
