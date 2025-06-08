package com.uhexastack.orderservice.order.application.internal.queryservices;


import com.uhexastack.orderservice.order.domain.model.entities.OrderStatus;
import com.uhexastack.orderservice.order.domain.model.queries.GetAllOrderStatusTypeQuery;
import com.uhexastack.orderservice.order.domain.services.OrderStatusQueryService;
import com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderStatusQueryServiceImpl implements OrderStatusQueryService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusQueryServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public Set<OrderStatus> handle(GetAllOrderStatusTypeQuery query) {
        return new java.util.HashSet<>(orderStatusRepository.findAll());
    }
}
