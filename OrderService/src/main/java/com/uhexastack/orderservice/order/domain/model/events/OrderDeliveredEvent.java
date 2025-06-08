package com.uhexastack.orderservice.order.domain.model.events;

public record OrderDeliveredEvent(Long orderId, Long userId) {
}
