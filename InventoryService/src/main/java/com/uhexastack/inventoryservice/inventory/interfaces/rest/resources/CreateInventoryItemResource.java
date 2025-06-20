package com.uhexastack.inventoryservice.inventory.interfaces.rest.resources;


import com.uhexastack.shared.domain.model.valuobjects.Money;

public record CreateInventoryItemResource(
        Long userId,
        String name,
        Money price,
        int initialQuantity,
        Integer threshold
) {
}