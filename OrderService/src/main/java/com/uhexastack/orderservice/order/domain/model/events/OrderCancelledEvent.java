package com.uhexastack.orderservice.order.domain.model.events;

public record OrderCancelledEvent(Long orderId, Long userId) {
}
