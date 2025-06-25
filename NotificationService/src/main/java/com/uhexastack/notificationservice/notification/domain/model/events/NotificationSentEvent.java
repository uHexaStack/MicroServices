package com.uhexastack.notificationservice.notification.domain.model.events;

/**
 * Represents an event indicating that a notification has been successfully sent.
 *
 * @param notificationId the unique identifier of the sent notification
 */
public record NotificationSentEvent(Long notificationId) {
}