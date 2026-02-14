package com.burakcanaksoy.healthtracking.produce;

import com.burakcanaksoy.healthtracking.config.QueueConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportProducer {
    private final JmsTemplate jmsTemplate;
    private final QueueConfig queueConfig;

    public void sendReport(ReportMessage reportMessage){
        jmsTemplate.convertAndSend(queueConfig.getReportQueue(),reportMessage);
    }
}
