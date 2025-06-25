package com.uhexastack.authservice.iam.domain.services;



import com.uhexastack.authservice.iam.domain.model.entities.Role;
import com.uhexastack.authservice.iam.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
