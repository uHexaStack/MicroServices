package com.uhexastack.notificationservice.notification.domain.model.queries;

/**
 * Represents a query to retrieve notifications by status.
 * <p>
 * This query validates that the status is not null or blank.
 * </p>
 *
 * @param status the status used to filter notifications
 */
public record GetNotificationsByStatusQuery(String status) {
    public GetNotificationsByStatusQuery {
        if (status == null || status.isBlank())
            throw new IllegalArgumentException("Status cannot be null or blank");
    }
}