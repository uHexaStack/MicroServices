package com.uhexastack.inventoryservice.inventory.interfaces.rest.resources;


import com.uhexastack.inventoryservice.shared.domain.model.valuobjects.Money;

public record InventoryItemResource(
        Long id,
        Long userId,
        String name,
        Money price,
        int quantityOnHand,
        int threshold
) {
}
