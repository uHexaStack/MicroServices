package com.uhexastack.notificationservice.shared.domain.model.events;

public record OrderShippedEvent(
    Long orderId,
    Long userId,
    String trackingNumber
) {} 