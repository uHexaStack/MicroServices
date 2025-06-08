package com.uhexastack.orderservice.order.domain.model.events;

public record OrderShippedEvent(Long orderId, Long userId) {
}
