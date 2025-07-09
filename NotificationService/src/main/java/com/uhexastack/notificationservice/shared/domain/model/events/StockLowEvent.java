package com.uhexastack.notificationservice.shared.domain.model.events;

public record StockLowEvent(
    Long productId,
    String name,
    Integer availableQuantity,
    Integer minimumThreshold
) {} 