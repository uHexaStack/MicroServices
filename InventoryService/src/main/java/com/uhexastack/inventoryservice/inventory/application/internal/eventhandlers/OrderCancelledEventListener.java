package com.uhexastack.inventoryservice.inventory.application.internal.eventhandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uhexastack.inventoryservice.inventory.domain.model.commands.ReleaseInventoryCommand;
import com.uhexastack.inventoryservice.inventory.domain.services.InventoryCommandService;
import com.uhexastack.shared.domain.model.events.OrderCancelledEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCancelledEventListener {

    private final InventoryCommandService inventoryCommandService;
    private final ObjectMapper objectMapper;

    public OrderCancelledEventListener(InventoryCommandService inventoryCommandService, ObjectMapper objectMapper) {
        this.inventoryCommandService = inventoryCommandService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order.cancelled", groupId = "inventory-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            OrderCancelledEvent event = objectMapper.readValue(record.value(), OrderCancelledEvent.class);
            event.getProductQuantities().forEach((productId, quantity) -> {
                inventoryCommandService.handle(new ReleaseInventoryCommand(productId, quantity));
            });
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
        }
    }
}

