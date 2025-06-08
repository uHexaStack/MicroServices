package com.uhexastack.orderservice.order.interfaces.rest.transform;


import com.uhexastack.orderservice.order.domain.model.entities.OrderStatus;
import com.uhexastack.orderservice.order.interfaces.rest.resources.OrderStatusResource;

public class OrderStatusResourceFromEntityAssembler {

    public static OrderStatusResource toResourceFromEntity(OrderStatus entity) {
        return new OrderStatusResource(
                entity.getId(),
                entity.getStringStatus()
        );
    }
}
