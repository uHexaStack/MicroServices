package com.uhexastack.orderservice.order.domain.model.events;

public record OrderCreatedEvent(Long orderId, Long userId) {
}
