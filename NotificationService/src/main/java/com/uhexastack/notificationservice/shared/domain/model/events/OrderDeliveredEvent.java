package com.uhexastack.notificationservice.shared.domain.model.events;

public record OrderDeliveredEvent(
    Long orderId,
    Long userId
) {} 