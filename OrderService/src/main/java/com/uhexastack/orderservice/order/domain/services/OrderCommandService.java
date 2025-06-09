package com.uhexastack.orderservice.order.domain.services;


import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import com.uhexastack.orderservice.order.domain.model.commands.*;

import java.util.Optional;

public interface OrderCommandService {

    Optional<OrderAggregate> handle(CreateOrderCommand command);

    void handle(ConfirmOrderCommand command);

    void handle(CancelOrderCommand command);

    void handle(ShipOrderCommand command);

    void handle(DeliverOrderCommand command);
}
