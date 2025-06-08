package com.uhexastack.inventoryservice.inventory.domain.model.events;

public record StockLowEvent(
        Long itemId,
        String name,
        int availableQuantity
) {
}
