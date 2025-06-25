package com.uhexastack.profileservice.profiles.interfaces.rest.transform;


import com.uhexastack.profileservice.profiles.domain.model.aggregates.ProfileAggregate;
import com.uhexastack.profileservice.profiles.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {

    public static ProfileResource toResourceFromEntity(ProfileAggregate entity) {
        return new ProfileResource(
                entity.getUserId(),
                entity.getFullName(),
                entity.getRuc(),
                entity.getContactEmail(),
                entity.getContactPhone(),
                entity.getCompanyInfo()
        );
    }
}
