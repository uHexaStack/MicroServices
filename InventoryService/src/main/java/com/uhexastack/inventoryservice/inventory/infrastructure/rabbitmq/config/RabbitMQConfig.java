package com.uhexastack.inventoryservice.inventory.infrastructure.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange.order}")
    private String orderExchangeName;

    @Value("${spring.rabbitmq.routing-key.order-cancelled}")
    private String orderCancelledRoutingKey;

    @Bean
    public Queue orderCancelledQueue() {
        return new Queue("order.cancelled.queue");
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(orderExchangeName);
    }

    @Bean
    public Binding bindingOrderCancelled(Queue orderCancelledQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(orderCancelledQueue)
                .to(orderExchange)
                .with(orderCancelledRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
