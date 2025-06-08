package com.uhexastack.orderservice.order.domain.services;



import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import com.uhexastack.orderservice.order.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {
    Optional<OrderAggregate> handle(GetOrderByIdQuery query);

    List<OrderAggregate> handle(GetOrdersByUserIdQuery query);
}
