package com.uhexastack.notificationservice.notification.interfaces.rest.resources;

public record CreateNotificationResource(
        String type,
        String recipient,
        String payload,
        Long orderId,
        Long invoiceId,
        Long paymentId
) { }