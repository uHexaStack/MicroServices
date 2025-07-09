package com.uhexastack.notificationservice.shared.infrastructure.messaging;

import com.uhexastack.notificationservice.shared.domain.model.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventListener.class);
    
    private final ApplicationEventPublisher eventPublisher;
    
    public KafkaEventListener(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    @KafkaListener(topics = "order-created", groupId = "notification-service-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        logger.info("Received OrderCreatedEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "order-cancelled", groupId = "notification-service-group")
    public void handleOrderCancelled(OrderCancelledEvent event) {
        logger.info("Received OrderCancelledEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "order-shipped", groupId = "notification-service-group")
    public void handleOrderShipped(OrderShippedEvent event) {
        logger.info("Received OrderShippedEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "order-delivered", groupId = "notification-service-group")
    public void handleOrderDelivered(OrderDeliveredEvent event) {
        logger.info("Received OrderDeliveredEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "payment-processed", groupId = "notification-service-group")
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        logger.info("Received PaymentProcessedEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "payment-failed", groupId = "notification-service-group")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        logger.info("Received PaymentFailedEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
    
    @KafkaListener(topics = "stock-low", groupId = "notification-service-group")
    public void handleStockLow(StockLowEvent event) {
        logger.info("Received StockLowEvent from Kafka: {}", event);
        eventPublisher.publishEvent(event);
    }
} 