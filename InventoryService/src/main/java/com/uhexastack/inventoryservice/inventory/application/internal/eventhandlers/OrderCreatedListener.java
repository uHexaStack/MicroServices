package com.uhexastack.inventoryservice.inventory.application.internal.eventhandlers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.uhexastack.inventoryservice.inventory.domain.model.commands.ReserveInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.shared.domain.model.events.OrderCreatedEvent;

@Service
public class OrderCreatedListener {

    private final InventoryCommandService inventoryCommandService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderCreatedListener(InventoryCommandService inventoryCommandService,
                                 KafkaTemplate<String, Object> kafkaTemplate) {
        this.inventoryCommandService = inventoryCommandService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderCreatedEvent event) {
        var orderId = event.orderId();
        var productQuantities = event.productQuantities();

        productQuantities.forEach((inventoryItemId, quantity) -> {
            try {
                var maybeLow = inventoryCommandService.handle(
                        new ReserveInventoryCommand(inventoryItemId, quantity)
                );
                maybeLow.ifPresent(lowEvent ->
                        kafkaTemplate.send("inventory.stock.low", lowEvent)
                );
            } catch (IllegalArgumentException ex) {
                System.err.println("Inventory reservation failed for inventoryItemId=" + inventoryItemId +
                        " in orderId=" + orderId + ": " + ex.getMessage());
            }
        });
    }
}