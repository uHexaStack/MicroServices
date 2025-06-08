package com.uhexastack.shared.domain.model.events;

import java.util.Map;

public class OrderCancelledEvent {
    private String orderId;
    private Map<String, Integer> productQuantities;

    public OrderCancelledEvent() {}

    public OrderCancelledEvent(String orderId, Map<String, Integer> productQuantities) {
        this.orderId = orderId;
        this.productQuantities = productQuantities;
    }

    public String getOrderId() { return orderId; }
    public Map<String, Integer> getProductQuantities() { return productQuantities; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setProductQuantities(Map<String, Integer> productQuantities) { this.productQuantities = productQuantities; }
}