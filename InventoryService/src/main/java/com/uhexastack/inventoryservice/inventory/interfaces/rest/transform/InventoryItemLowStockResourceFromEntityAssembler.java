package com.uhexastack.inventoryservice.inventory.interfaces.rest.transform;


import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.InventoryItemLowStockResource;

/**
 * Assembler for converting an {@code InventoryItemAggregate} entity into an {@code InventoryItemLowStockResource}.
 */
public class InventoryItemLowStockResourceFromEntityAssembler {

    /**
     * Converts the given inventory item aggregate into a low stock resource.
     *
     * @param resource the inventory item aggregate to convert
     * @return the corresponding {@code InventoryItemLowStockResource}
     */
    public static InventoryItemLowStockResource toResourceFromEntity(InventoryItemAggregate resource) {
        return new InventoryItemLowStockResource(
                resource.getUserId(),
                resource.getName(),
                resource.getQuantityOnHand(),
                resource.getThreshold()
        );
    }
}