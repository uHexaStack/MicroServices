package com.uhexastack.orderservice.order.domain.model.events;

public record OrderConfirmedEvent(Long orderId, Long userId) {
}
