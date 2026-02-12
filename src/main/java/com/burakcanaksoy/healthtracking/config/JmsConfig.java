package com.burakcanaksoy.healthtracking.config;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.JacksonJsonMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class JmsConfig {
    private final QueueConfig queueConfig;

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory,
                                   MessageConverter converter){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setMessageConverter(converter);
        return jmsTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(queueConfig.getBrokerUrl());
        factory.setUserName(queueConfig.getUsername());
        factory.setPassword(queueConfig.getPassword());
        factory.setTrustedPackages(List.of("com.burakcanaksoy.healthtracking"));

        PooledConnectionFactory pooled = new PooledConnectionFactory();
        pooled.setConnectionFactory(factory);
        pooled.setMaxConnections(10);

        return pooled;
    }

    @Bean
    public MessageConverter converter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public Queue reportQueue() {
        return new ActiveMQQueue(queueConfig.getReportQueue());
    }

}
