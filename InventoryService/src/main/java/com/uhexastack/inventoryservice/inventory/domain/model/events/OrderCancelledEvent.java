package com.uhexastack.inventoryservice.inventory.domain.model.events;

public record OrderCancelledEvent(
        Long orderId, Long userId
) {
}
