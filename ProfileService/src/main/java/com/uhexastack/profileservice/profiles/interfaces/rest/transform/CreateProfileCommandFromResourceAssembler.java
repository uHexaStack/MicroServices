package com.uhexastack.profileservice.profiles.interfaces.rest.transform;


import com.uhexastack.profileservice.profiles.domain.model.commands.CreateProfileCommand;
import com.uhexastack.profileservice.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {

    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.userId(),
                resource.firstName(),
                resource.lastName(),
                resource.ruc(),
                resource.contactEmail(),
                resource.contactPhone(),
                resource.companyName(),
                resource.companyStreet(),
                resource.companyCity(),
                resource.postalCode(),
                resource.companyCountry()
        );
    }
}
