package com.uhexastack.paymentservice.payment.interfaces.acl;

/**
 * Facade interface for order context operations.
 * Provides methods to retrieve order-related information.
 */
public interface OrderContextFacade {

    /**
     * Retrieves the total amount of an order.
     *
     * @param orderId the unique identifier of the order
     * @return the total amount of the order
     */
    double getOrderTotal(Long orderId);
} 