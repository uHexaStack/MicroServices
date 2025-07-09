package com.uhexastack.notificationservice.shared.domain.model.events;

public record PaymentProcessedEvent(
    Long paymentId,
    Long orderId,
    Long userId
) {} 