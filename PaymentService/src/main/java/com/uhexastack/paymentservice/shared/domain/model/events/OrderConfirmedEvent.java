package com.uhexastack.paymentservice.shared.domain.model.events;

/**
 * Event that is published when an order is confirmed.
 * 
 * @param userId the unique identifier of the user
 * @param orderId the unique identifier of the order
 */
public record OrderConfirmedEvent(
    Long userId,
    Long orderId
) {} 