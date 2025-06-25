package com.uhexastack.notificationservice.notification.application.internal.comandservices;


import com.uhexastack.notificationservice.notification.application.internal.outboundservices.NotificationSender;
import com.uhexastack.notificationservice.notification.domain.model.aggregates.NotificationAggregate;
import com.uhexastack.notificationservice.notification.domain.model.commands.SendNotificationCommand;
import com.uhexastack.notificationservice.notification.domain.model.entities.NotificationStatus;
import com.uhexastack.notificationservice.notification.domain.model.events.*;
import com.uhexastack.notificationservice.notification.domain.model.valueobjects.*;
import com.uhexastack.notificationservice.notification.domain.services.NotificationCommandService;
import com.uhexastack.notificationservice.notification.infrastructure.persistence.jpa.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository typeRepo;
    private final NotificationStatusRepository statusRepo;
    private final NotificationSender outbound;
    private final ApplicationEventPublisher eventPublisher;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository, NotificationTypeRepository typeRepo, NotificationStatusRepository statusRepo, NotificationSender outbound, ApplicationEventPublisher eventPublisher) {
        this.notificationRepository = notificationRepository;
        this.typeRepo = typeRepo;
        this.statusRepo = statusRepo;
        this.outbound = outbound;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Optional<NotificationAggregate> handle(SendNotificationCommand cmd) {
        var type = typeRepo.findByName(NotificationTypeType.valueOf(cmd.type()))
                .orElseThrow();

        var pending = statusRepo.findByName(NotificationStatusType.PENDING)
                .orElseThrow();

        var notif = NotificationAggregate.create(cmd, type, pending);
        notificationRepository.save(notif);

        boolean sent = outbound.send(cmd.recipient(), cmd.payload());
        if (sent) {
            NotificationStatus sentStatus = statusRepo.findByName(NotificationStatusType.SENT)
                    .orElseThrow();

            notif.setStatus(sentStatus);
            notif.setSentAt(Instant.now());

            notificationRepository.save(notif);
            eventPublisher.publishEvent(new NotificationSentEvent(notif.getId()));
        } else {
            NotificationStatus failStatus = statusRepo.findByName(NotificationStatusType.FAILED).orElseThrow();

            notif.setStatus(failStatus);

            notificationRepository.save(notif);
            eventPublisher.publishEvent(new NotificationFailedEvent(notif.getId(), "Send failure"));
        }
        return Optional.of(notif);
    }
}
