package com.uhexastack.orderservice.order.interfaces.rest.transform;


import com.uhexastack.orderservice.order.domain.model.commands.CreateOrderCommand;
import com.uhexastack.orderservice.order.domain.model.valueobjects.ShippingAddress;
import com.uhexastack.orderservice.order.interfaces.rest.resources.CreateOrderResource;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommandFromResource(CreateOrderResource command) {

        var shippingAddress = new ShippingAddress(
                command.shippingAddress().street(),
                command.shippingAddress().city(),
                command.shippingAddress().postalCode(),
                command.shippingAddress().country()
        );

        return new CreateOrderCommand(
                command.userId(),
                shippingAddress,
                command.lines().stream()
                        .map(line -> new CreateOrderCommand.Line(
                                line.productId(),
                                line.quantity(),
                                line.unitPrice(),
                                line.currency()
                        )).toList()
        );
    }
}
