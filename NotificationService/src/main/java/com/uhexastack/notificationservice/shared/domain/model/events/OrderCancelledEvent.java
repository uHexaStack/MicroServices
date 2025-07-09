package com.uhexastack.notificationservice.shared.domain.model.events;

import java.util.Map;

public record OrderCancelledEvent(
    Long orderId,
    Map<Long, Integer> productQuantities // inventoryItemId -> quantity
) {}
