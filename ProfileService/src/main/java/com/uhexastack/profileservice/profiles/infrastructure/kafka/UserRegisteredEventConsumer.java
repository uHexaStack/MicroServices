package com.uhexastack.profileservice.profiles.infrastructure.kafka;

import com.uhexastack.profileservice.profiles.application.acl.ProfileContextFacadeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisteredEventConsumer.class);

    private final ProfileContextFacadeImpl profileContextFacade;

    public UserRegisteredEventConsumer(ProfileContextFacadeImpl profileContextFacade) {
        this.profileContextFacade = profileContextFacade;
    }

    @KafkaListener(
        topics = "user-registered",
        groupId = "profile-service-group-test",
        containerFactory = "userRegisteredEventKafkaListenerContainerFactory"
    )
    public void handleUserRegisteredEvent(
            @Payload UserRegisteredEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset
    ) {
        logger.info("Received user registered event from topic: {}, partition: {}, offset: {}", 
                   topic, partition, offset);
        logger.info("UserRegisteredEvent received: {}", event);
        logger.info("Processing user registration for user ID: {}", event.userId());

        try {
            profileContextFacade.createProfile(
                    event.userId(),
                    event.firstName(),
                    event.lastName(),
                    event.ruc(),
                    event.contactEmail(),
                    event.contactPhone(),
                    event.companyName(),
                    event.companyStreet(),
                    event.companyCity(),
                    event.postalCode(),
                    event.companyCountry()
            );
            
            logger.info("Profile created successfully for user ID: {}", event.userId());
        } catch (Exception e) {
            logger.error("Failed to create profile for user ID: {}", event.userId(), e);
            throw e; // Re-throw to trigger Kafka retry mechanism
        }
    }
} 