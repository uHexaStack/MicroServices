package com.uhexastack.paymentservice.payment.infrastructure.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsListener {
    private static final Logger logger = LoggerFactory.getLogger(PaymentEventsListener.class);

    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void listen(Object event) {
        logger.info("Received event from Kafka: {}", event);
    }
} 