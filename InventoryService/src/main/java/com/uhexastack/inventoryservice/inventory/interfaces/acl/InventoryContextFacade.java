package com.uhexastack.inventoryservice.inventory.interfaces.acl;

import com.uhexastack.shared.domain.model.valuobjects.Money;

import java.util.List;

public interface InventoryContextFacade {

    Long createInventoryItem(Long userId, String name, Money price, int initialQuantity, int threshold);

    boolean adjustInventory(Long itemId, int adjustBy);

    boolean reserveStock(Long itemId, int quantity);

    boolean releaseStock(Long itemId, int quantity);

    int getAvailableQuantity(Long userId, String name);

    List<String> getItemsWithLowStock(Long userId, String name);
}