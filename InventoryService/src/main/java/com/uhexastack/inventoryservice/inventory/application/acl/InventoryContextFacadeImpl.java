package com.uhexastack.inventoryservice.inventory.application.acl;

import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.uhexastack.inventoryservice.inventory.domain.model.commands.AdjustInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.uhexastack.inventoryservice.inventory.domain.model.commands.ReserveInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetInventoryByUserIdAndNameQuery;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetLowStockItemByNameQuery;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryQueryService;
import com.uhexastack.inventoryservice.inventory.interfaces.acl.InventoryContextFacade;
import com.uhexastack.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.uhexastack.shared.domain.model.valuobjects.Money;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    public InventoryContextFacadeImpl(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @Override
    public Long createInventoryItem(Long userId, String name, Money price, int initialQuantity, int threshold) {
        var command = new CreateInventoryItemCommand(userId, name, price, initialQuantity, threshold);

        return inventoryCommandService.handle(command)
                .map(AuditableAbstractAggregateRoot::getId)
                .orElse(0L);
    }

    @Override
    public boolean adjustInventory(Long itemId, int adjustBy) {
        try {
            var command = new AdjustInventoryCommand(itemId, adjustBy);
            inventoryCommandService.handle(command);
            return true;
        } catch (
                Exception e) {
            return false;
        }
    }

    @Override
    public boolean reserveStock(Long itemId, int quantity) {
        try {
            var command = new ReserveInventoryCommand(itemId, quantity);
            inventoryCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean releaseStock(Long itemId, int quantity) {
        try {
            var command = new ReserveInventoryCommand(itemId, -quantity);
            inventoryCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getAvailableQuantity(Long userId, String name) {
        var query = new GetInventoryByUserIdAndNameQuery(userId, name);

        var maybe = inventoryQueryService.handle(query);

        return maybe.map(InventoryItemAggregate::getQuantityOnHand).orElse(-1);
    }

    @Override
    public List<String> getItemsWithLowStock(Long userId, String name) {
        var query = new GetLowStockItemByNameQuery(userId, name);
        var items = inventoryQueryService.handle(query);

        return items.stream()
                .map(InventoryItemAggregate::getName)
                .toList();
    }
}
