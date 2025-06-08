package com.uhexastack.orderservice.order.domain.services;


import com.uhexastack.orderservice.order.domain.model.commands.SeedOrderStatusesCommand;

public interface OrderStatusCommandService {

    void handle(SeedOrderStatusesCommand command);
}
