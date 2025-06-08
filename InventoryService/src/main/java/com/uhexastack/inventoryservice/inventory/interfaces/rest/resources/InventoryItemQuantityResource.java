package com.uhexastack.inventoryservice.inventory.interfaces.rest.resources;

public record InventoryItemQuantityResource(
        Long userId,
        String name,
        int quantityOnHand
) {
}
