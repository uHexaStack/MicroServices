package com.uhexastack.inventoryservice.inventory.application.internal.eventhandlers;

import com.uhexastack.inventoryservice.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.shared.domain.model.events.OrderCancelledEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

        /**
         * Event handler for processing `OrderCancelledEvent` messages.
         * This service listens to RabbitMQ messages and handles inventory release
         * for the products in the cancelled order.
         */
        @Service
        public class OrderCancelledEventHandler {

            private final InventoryCommandService inventoryCommandService;

            /**
             * Constructor for `OrderCancelledEventHandler`.
             *
             * @param inventoryCommandService Service for handling inventory commands.
             */
            public OrderCancelledEventHandler(InventoryCommandService inventoryCommandService) {
                this.inventoryCommandService = inventoryCommandService;
            }

            /**
             * Listener method for handling `OrderCancelledEvent` messages from RabbitMQ.
             * Processes the event by releasing inventory for each product in the cancelled order.
             *
             * @param event The `OrderCancelledEvent` containing order details.
             */
            @RabbitListener(queues = "order.cancelled.queue")
            public void handleOrderCancelled(OrderCancelledEvent event) {
                // Iterate through the product quantities in the order and release inventory.
                event.getProductQuantities().forEach((productId, quantity) -> {
                    // Handle the release of inventory for the given product and quantity.
                    inventoryCommandService.handle(new ReleaseInventoryCommand(productId, quantity));
                });
            }
        }