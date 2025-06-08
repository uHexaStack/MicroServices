package com.uhexastack.inventoryservice.inventory.infrastructure.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue orderCancelledQueue() {
        return new Queue("order.cancelled.queue");
    }

    @Bean
    public TopicExchange orderExchange(@Value("${rabbitmq.exchange.order}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding bindingOrderCancelled(Queue orderCancelledQueue,
                                         TopicExchange orderExchange,
                                         @Value("${rabbitmq.routing-key.order-cancelled}") String routingKey) {
        return BindingBuilder.bind(orderCancelledQueue).to(orderExchange).with(routingKey);
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
    @Value("${spring.rabbitmq.exchange.order}")
    private String orderExchange;


}
