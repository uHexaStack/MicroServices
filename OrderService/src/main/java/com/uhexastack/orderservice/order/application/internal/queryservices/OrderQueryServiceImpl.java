package com.uhexastack.orderservice.order.application.internal.queryservices;


import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import com.uhexastack.orderservice.order.domain.model.queries.GetOrderByIdQuery;
import com.uhexastack.orderservice.order.domain.model.queries.GetOrdersByUserIdQuery;
import com.uhexastack.orderservice.order.domain.services.OrderQueryService;
import com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderAggregate> handle(GetOrderByIdQuery query) {
        return orderRepository.findById(query.orderId());
    }

    @Override
    public List<OrderAggregate> handle(GetOrdersByUserIdQuery query) {
        return orderRepository.findByUserId(query.userId());
    }
}
