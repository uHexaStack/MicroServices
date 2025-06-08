package com.uhexastack.inventoryservice.inventory.interfaces.rest.transform;


import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.InventoryItemResource;

public class InventoryItemResourceFromEntityAssembler {

    public static InventoryItemResource toResourceFromEntity(InventoryItemAggregate entity) {
        return new InventoryItemResource(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantityOnHand(),
                entity.getThreshold()
        );
    }
}
