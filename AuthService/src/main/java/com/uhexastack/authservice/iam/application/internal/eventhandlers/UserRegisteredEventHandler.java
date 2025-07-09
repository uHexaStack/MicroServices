package com.uhexastack.authservice.iam.application.internal.eventhandlers;

import com.uhexastack.authservice.iam.domain.model.events.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@Service
public class UserRegisteredEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisteredEventHandler.class);
    private static final String USER_REGISTERED_TOPIC = "user-registered";

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public UserRegisteredEventHandler(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void onUserRegistered(UserRegisteredEvent event) {
        logger.info("Publishing user registered event to Kafka: {}", event.userId());
        
        CompletableFuture<SendResult<String, UserRegisteredEvent>> future = 
            kafkaTemplate.send(USER_REGISTERED_TOPIC, event.userId().toString(), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("User registered event sent successfully to Kafka for user: {}", event.userId());
            } else {
                logger.error("Failed to send user registered event to Kafka for user: {}", event.userId(), ex);
            }
        });
    }
}
