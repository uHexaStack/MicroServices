package com.uhexastack.authservice.iam.interfaces.rest.transform;


import com.uhexastack.authservice.iam.domain.model.entities.Role;
import com.uhexastack.authservice.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler for converting a {@link Role} entity into a {@link RoleResource} representation.
 */
public class RoleResourceFromEntityAssembler {

    /**
     * Converts a {@link Role} entity into a {@link RoleResource}.
     *
     * @param entity the role entity to convert
     * @return the corresponding {@link RoleResource}
     */
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(
                entity.getId(),
                entity.getStringName());
    }
}