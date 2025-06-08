package com.uhexastack.inventoryservice.inventory.interfaces.rest;


import com.uhexastack.inventoryservice.inventory.domain.model.commands.AdjustInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryQueryService;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.CreateInventoryItemResource;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.InventoryItemResource;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.transform.CreateInventoryItemCommandFromResourceAssembler;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.transform.InventoryItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory", description = "Inventory Management Endpoints")
public class InventoriesController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;

    public InventoriesController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create inventory item", description = "Creates a new inventory item with initial quantity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory item created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or item already exists")
    })
    public ResponseEntity<InventoryItemResource> createInventoryItem(
            @RequestBody CreateInventoryItemResource resource
    ) {
        var command = CreateInventoryItemCommandFromResourceAssembler.toCommandFromResource(resource);
        var item = inventoryCommandService.handle(command);

        if (item.isEmpty())
            return ResponseEntity.badRequest().build();

        var itemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(item.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(itemResource);
    }

    @PutMapping("/adjust/{itemId}")
    @Operation(summary = "Adjust inventory", description = "Adjust stock (positive to add, negative to remove)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock adjusted"),
            @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<InventoryItemResource> adjustInventory(
            @PathVariable Long itemId,
            @RequestParam int adjustBy
    ) {
        try {
            inventoryCommandService.handle(
                    new AdjustInventoryCommand(itemId, adjustBy));

            return getInventoryItemResponse(itemId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{itemId}/reserve")
    public ResponseEntity<InventoryItemResource> reserveStock(
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        try {
            inventoryCommandService.handle(
                    new ReserveInventoryCommand(itemId, quantity));

            return getInventoryItemResponse(itemId);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{itemId}/release")
    @Operation(summary = "Release stock", description = "Release previously reserved stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock released"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ResponseEntity<InventoryItemResource> releaseStock(
            @PathVariable Long itemId,
            @RequestParam int quantity
    ) {
        var command = new ReleaseInventoryCommand(itemId, quantity);

        try {
            inventoryCommandService.handle(command);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return getInventoryItemResponse(itemId);
    }

    @GetMapping("/users/{userId}/items/{name}")
    @Operation(summary = "Get available quantity", description = "Get available stock for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<InventoryItemQuantityResource> getAvailableQuantity(
            @PathVariable Long userId,
            @PathVariable String name
    ) {
        var query = new GetInventoryByUserIdAndNameQuery(userId, name);
        var maybeItem = inventoryQueryService.handle(query);

        if (maybeItem.isEmpty())
            return ResponseEntity.notFound().build();

        var resource = InventoryItemQuantityResourceFromEntityAssembler.toResourceFromEntity(maybeItem.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/users/{userId}/{name}/low-stock")
    @Operation(summary = "Get item with low stock", description = "Get item with low stock by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low stock item found"),
            @ApiResponse(responseCode = "404", description = "Low stock item not found"),
    })
    public ResponseEntity<InventoryItemLowStockResource> getLowStockItem(
            @PathVariable Long userId,
            @PathVariable String name
    ) {
        var query = new GetLowStockItemByNameQuery(userId, name);
        var item = inventoryQueryService.handle(query);

        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = InventoryItemLowStockResourceFromEntityAssembler
                .toResourceFromEntity(item.get());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get items with low stock", description = "Get item list with low stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Low stock items found"),
            @ApiResponse(responseCode = "404", description = "Low stock items not found"),
    })
    public ResponseEntity<List<InventoryItemLowStockResource>> getLowStockItems() {
        var query = new GetLowStockItemsQuery();
        var items = inventoryQueryService.handle(query);

        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = items.stream()
                .map(InventoryItemLowStockResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    private ResponseEntity<InventoryItemResource> getInventoryItemResponse(Long itemId) {
        var query = new GetInventoryItemByIdQuery(itemId);
        var maybeItem = inventoryQueryService.handle(query);

        if (maybeItem.isEmpty())
            return ResponseEntity.notFound().build();

        var resource = InventoryItemResourceFromEntityAssembler
                .toResourceFromEntity(maybeItem.get());

        return ResponseEntity.ok(resource);
    }
}