package com.uhexastack.shared.domain.model.events;

import java.util.Map;

public class OrderCancelledEvent {
    // existing fields and methods

    private Map<Long, Integer> productQuantities;

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
