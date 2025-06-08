package com.uhexastack.inventoryservice.inventory.interfaces.rest.transform;


import com.uhexastack.inventoryservice.inventory.domain.model.commands.CreateInventoryItemCommand;
import com.uhexastack.inventoryservice.inventory.interfaces.rest.resources.CreateInventoryItemResource;

/**
 * Assembler to create a CreateInventoryItemCommand from a CreateInventoryItemResource.
 */
public class CreateInventoryItemCommandFromResourceAssembler {

    /**
     * Converts a CreateInventoryItemResource to a CreateInventoryItemCommand.
     *
     * @param resource the resource containing the data to create the command.
     * @return a new instance of CreateInventoryItemCommand.
     */
    public static CreateInventoryItemCommand toCommandFromResource(CreateInventoryItemResource resource) {
        return new CreateInventoryItemCommand(
                resource.userId(),
                resource.name(),
                resource.price(),
                resource.initialQuantity(),
                resource.threshold()
        );
    }
}