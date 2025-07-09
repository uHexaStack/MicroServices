package com.uhexastack.notificationservice.shared.domain.model.events;

public record PaymentFailedEvent(
    Long orderId,
    String reason
) {} 