package com.uhexastack.notificationservice.notification.infrastructure.persistence.jpa.repositories;


import com.uhexastack.notificationservice.notification.domain.model.entities.NotificationStatus;
import com.uhexastack.notificationservice.notification.domain.model.valueobjects.NotificationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Long> {

    Optional<NotificationStatus> findByName(NotificationStatusType name);

    boolean existsByName(NotificationStatusType name);
}