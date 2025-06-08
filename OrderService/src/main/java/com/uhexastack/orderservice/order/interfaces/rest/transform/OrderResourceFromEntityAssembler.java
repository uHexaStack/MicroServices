package com.uhexastack.orderservice.order.interfaces.rest.transform;


import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import com.uhexastack.orderservice.order.interfaces.rest.resources.OrderResource;

public class OrderResourceFromEntityAssembler {

    public static OrderResource toResourceFromEntity(OrderAggregate entity) {
        return new OrderResource(
                entity.getId(),
                entity.getUserId(),
                entity.getStatus().getStringStatus(),
                entity.getShippingAddress().toString(),
                entity.getTotal().amount()
        );
    }
}
