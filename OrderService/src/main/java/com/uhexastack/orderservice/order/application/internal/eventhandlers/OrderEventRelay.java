package com.uhexastack.orderservice.order.application.internal.eventhandlers;

import com.uhexastack.orderservice.order.domain.model.events.OrderCancelledEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventRelay {
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public OrderEventRelay(RabbitTemplate rabbitTemplate,
                           @Value("${rabbitmq.exchange.order}") String exchange,
                           @Value("${rabbitmq.routing-key.order-cancelled}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @EventListener
    public void handleOrderCancelled(OrderCancelledEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
