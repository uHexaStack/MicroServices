package com.uhexastack.orderservice.order.domain.services;



import com.uhexastack.orderservice.order.domain.model.entities.OrderStatus;
import com.uhexastack.orderservice.order.domain.model.queries.GetAllOrderStatusTypeQuery;

import java.util.Set;

public interface OrderStatusQueryService {

    Set<OrderStatus> handle(GetAllOrderStatusTypeQuery query);
}
