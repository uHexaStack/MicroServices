package com.uhexastack.notificationservice.notification.application.internal.eventhandlers;


import com.uhexastack.notificationservice.notification.domain.model.entities.NotificationStatus;
import com.uhexastack.notificationservice.notification.domain.model.entities.NotificationType;
import com.uhexastack.notificationservice.notification.domain.model.valueobjects.NotificationStatusType;
import com.uhexastack.notificationservice.notification.domain.model.valueobjects.NotificationTypeType;
import com.uhexastack.notificationservice.notification.infrastructure.persistence.jpa.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationApplicationReadyEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationApplicationReadyEventHandler.class);
    private final NotificationTypeRepository typeRepo;
    private final NotificationStatusRepository statusRepo;

    public NotificationApplicationReadyEventHandler(NotificationTypeRepository typeRepo, NotificationStatusRepository statusRepo) {
        this.typeRepo = typeRepo;
        this.statusRepo = statusRepo;
    }

    @EventListener
    public void on(ApplicationReadyEvent ev) {
        LOG.info("Seeding Types and Statuses of Notifications...");

        for (NotificationTypeType type : NotificationTypeType.values())
            if (!typeRepo.existsByName(type))
                typeRepo.save(new NotificationType(type));

        LOG.info("Seeding Types of Notifications complete.");
        for (NotificationStatusType state : NotificationStatusType.values())
            if (!statusRepo.existsByName(state))
                statusRepo.save(new NotificationStatus(state));

        LOG.info("Seeding Statuses of Notifications complete.");
    }
}
