package com.uhexastack.orderservice.order.application.internal.commandservices;


import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import com.uhexastack.orderservice.order.domain.model.commands.*;
import com.uhexastack.orderservice.order.domain.model.events.*;
import com.uhexastack.orderservice.order.domain.model.valueobjects.OrderStatusType;
import com.uhexastack.orderservice.order.domain.services.OrderCommandService;
import com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories.OrderRepository;
import com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories.OrderStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher events;
    private final OrderStatusRepository orderStatusRepository;

    public OrderCommandServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, ApplicationEventPublisher events) {
        this.events = events;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Transactional
    @Override
    public Optional<OrderAggregate> handle(CreateOrderCommand command) {

        var created = orderStatusRepository.findByName(OrderStatusType.CREATED)
                .orElseThrow(() -> new IllegalStateException("Created order status not found"));

        var order = OrderAggregate.create(command, created);

        var saved = orderRepository.save(order);

        events.publishEvent(new OrderCreatedEvent(
                saved.getId(),
                command.userId()
        ));

        return Optional.of(order);
    }

    @Override
    public void handle(ConfirmOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var confirmed = orderStatusRepository.findByName(OrderStatusType.CONFIRMED)
                .orElseThrow(() -> new IllegalStateException("Confirmed order status not found"));

        order.setStatus(confirmed);

        orderRepository.save(order);

        events.publishEvent(new OrderConfirmedEvent(
                command.orderId(),
                order.getUserId()
        ));
    }

    @Override
    public void handle(CancelOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var cancelled = orderStatusRepository.findByName(OrderStatusType.CANCELLED)
                .orElseThrow(() -> new IllegalStateException("Cancelled order status not found"));

        order.setStatus(cancelled);

        orderRepository.save(order);

        events.publishEvent(new OrderCancelledEvent(
                command.orderId(),
                order.getUserId()
        ));

    }

    @Override
    public void handle(ShipOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var shipped = orderStatusRepository.findByName(OrderStatusType.SHIPPED)
                .orElseThrow(() -> new IllegalStateException("Shipped order status not found"));

        order.setStatus(shipped);

        orderRepository.save(order);

        events.publishEvent(new OrderShippedEvent(
                command.orderId(),
                order.getUserId()
        ));

    }

    @Override
    public void handle(DeliverOrderCommand command) {
        var order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var delivered = orderStatusRepository.findByName(OrderStatusType.DELIVERED)
                .orElseThrow(() -> new IllegalStateException("Delivered order status not found"));

        order.setStatus(delivered);

        orderRepository.save(order);

        events.publishEvent(new OrderDeliveredEvent(
                command.orderId(),
                order.getUserId()
        ));

    }
}
