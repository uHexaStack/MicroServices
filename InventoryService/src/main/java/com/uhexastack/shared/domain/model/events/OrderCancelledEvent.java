package com.uhexastack.shared.domain.model.events;

    import java.util.Map;

public class OrderCancelledEvent {
    private Long orderId;
    private Map<Long, Integer> productQuantities;

    public OrderCancelledEvent() {}

    public OrderCancelledEvent(Long orderId, Map<Long, Integer> productQuantities) {
        this.orderId = orderId;
        this.productQuantities = productQuantities;
    }

    public Long getOrderId() { return orderId; }
    public Map<Long, Integer> getProductQuantities() { return productQuantities; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setProductQuantities(Map<Long, Integer> productQuantities) { this.productQuantities = productQuantities; }
}
