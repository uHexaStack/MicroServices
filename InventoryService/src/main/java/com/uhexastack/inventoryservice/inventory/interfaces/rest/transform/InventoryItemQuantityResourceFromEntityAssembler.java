package com.uhexastack.inventoryservice.inventory.interfaces.rest.transform;


import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.InventoryItemQuantityResource;

public class InventoryItemQuantityResourceFromEntityAssembler {
    public static InventoryItemQuantityResource toResourceFromEntity(InventoryItemAggregate entity) {
        return new InventoryItemQuantityResource(
                entity.getUserId(),
                entity.getName(),
                entity.getQuantityOnHand()
        );
    }
}
