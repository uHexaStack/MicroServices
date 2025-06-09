package com.uhexastack.orderservice.order.infrastructure.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order.created.queue");
    }

    @Bean
    public TopicExchange orderExchange(@Value("${rabbitmq.exchange.order}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding bindingOrderCreated(Queue orderCreatedQueue,
                                       TopicExchange orderExchange,
                                       @Value("${rabbitmq.routing-key.order-created}") String routingKey) {
        return BindingBuilder.bind(orderCreatedQueue).to(orderExchange).with(routingKey);
    }
}