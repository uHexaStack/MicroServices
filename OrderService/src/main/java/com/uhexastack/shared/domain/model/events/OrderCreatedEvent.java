package com.uhexastack.shared.domain.model.events;

import java.io.Serializable;

public class OrderCreatedEvent implements Serializable {
    private Long orderId;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long orderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}