package com.uhexastack.inventoryservice.inventory.domain.services;



import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetInventoryByUserIdAndNameQuery;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetInventoryItemByIdQuery;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetLowStockItemByNameQuery;
import com.uhexastack.inventoryservice.inventory.domain.model.queries.GetLowStockItemsQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryQueryService {


    Optional<InventoryItemAggregate> handle(GetInventoryByUserIdAndNameQuery query);


    Optional<InventoryItemAggregate> handle(GetLowStockItemByNameQuery query);

    List<InventoryItemAggregate> handle(GetLowStockItemsQuery query);

    Optional<InventoryItemAggregate> handle(GetInventoryItemByIdQuery query);
}