package com.uhexastack.authservice.iam.domain.services;


import com.uhexastack.authservice.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}