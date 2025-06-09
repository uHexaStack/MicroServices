package com.uhexastack.orderservice.order.application.internal.commandservices;


import com.uhexastack.orderservice.order.domain.model.commands.SeedOrderStatusesCommand;
import com.uhexastack.orderservice.order.domain.model.entities.OrderStatus;
import com.uhexastack.orderservice.order.domain.model.valueobjects.OrderStatusType;
import com.uhexastack.orderservice.order.domain.services.OrderStatusCommandService;
import com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OrderStatusCommandServiceImpl implements OrderStatusCommandService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusCommandServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void handle(SeedOrderStatusesCommand command) {
        Arrays.stream(OrderStatusType.values()).forEach(orderStatusType -> {
            if (!orderStatusRepository.existsByName(orderStatusType))
                orderStatusRepository.save(OrderStatus.create(orderStatusType.name()));
        });
    }
}
