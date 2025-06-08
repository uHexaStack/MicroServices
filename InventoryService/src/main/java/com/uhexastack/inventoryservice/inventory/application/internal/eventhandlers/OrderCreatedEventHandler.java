package com.uhexastack.inventoryservice.inventory.application.internal.eventhandlers;

import com.uhexastack.inventoryservice.inventory.domain.model.commands.ReserveInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.shared.domain.model.events.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Event handler for processing `OrderCreatedEvent` messages.
 * This service listens to RabbitMQ messages and handles inventory reservation
 * for the products in the created order.
 */
@Service
public class OrderCreatedEventHandler {

    private final InventoryCommandService inventoryCommandService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor for `OrderCreatedEventHandler`.
     *
     * @param inventoryCommandService Service for handling inventory commands.
     * @param eventPublisher Publisher for application events.
     */
    public OrderCreatedEventHandler(InventoryCommandService inventoryCommandService,
                                    ApplicationEventPublisher eventPublisher) {
        this.inventoryCommandService = inventoryCommandService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Listener method for handling `OrderCreatedEvent` messages from RabbitMQ.
     * Processes the event by reserving inventory for each product in the order.
     *
     * @param event The `OrderCreatedEvent` containing order details.
     */
    @RabbitListener(queues = "order.created.queue")
    public void onOrderCreated(OrderCreatedEvent event) {
        Long orderId = event.orderId();

        // Iterate through the product quantities in the order and reserve inventory.
        event.productQuantities().forEach((inventoryItemId, quantity) -> {
            try {
                // Attempt to reserve inventory for the given product and quantity.
                var maybeLow = inventoryCommandService.handle(
                        new ReserveInventoryCommand(inventoryItemId, quantity)
                );

                // If inventory is low, publish a low inventory event.
                maybeLow.ifPresent(eventPublisher::publishEvent);
            } catch (IllegalArgumentException ex) {
                // Log an error if inventory reservation fails.
                System.err.println("Inventory reservation failed for inventoryItemId=" + inventoryItemId +
                        " in orderId=" + orderId + ": " + ex.getMessage());
            }
        });
    }
}