package com.uhexastack.inventoryservice.inventory.domain.model.commands;

import com.uhexastack.inventoryservice.shared.domain.model.valuobjects.Money;

public record CreateInventoryItemCommand(
        Long userId,
        String name,
        Money price,
        int quantityOnHand,
        int threshold
) {

    public CreateInventoryItemCommand {

        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("Invalid user ID");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Invalid name");

        if (price == null || price.amount().signum() < 0)
            throw new IllegalArgumentException("Invalid price");

        if (quantityOnHand < 0)
            throw new IllegalArgumentException("Initial quantity cannot be negative");

        if (threshold < 0)
            throw new IllegalArgumentException("Threshold cannot be negative");
    }
}
