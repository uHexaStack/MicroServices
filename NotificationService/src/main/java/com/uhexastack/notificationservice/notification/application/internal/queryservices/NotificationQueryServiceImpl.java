package com.uhexastack.notificationservice.notification.application.internal.queryservices;


import com.uhexastack.notificationservice.notification.domain.model.aggregates.NotificationAggregate;
import com.uhexastack.notificationservice.notification.domain.model.queries.*;
import com.uhexastack.notificationservice.notification.domain.model.valueobjects.NotificationStatusType;
import com.uhexastack.notificationservice.notification.domain.services.NotificationQueryService;
import com.uhexastack.notificationservice.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationAggregate> handle(GetNotificationsByStatusQuery query) {
        return notificationRepository.findByStatus_Name(NotificationStatusType.valueOf(query.status()));
    }

    @Override
    public List<NotificationAggregate> handle(GetNotificationsByRecipientQuery query) {
        return notificationRepository.findByRecipient(query.recipient());
    }
}
