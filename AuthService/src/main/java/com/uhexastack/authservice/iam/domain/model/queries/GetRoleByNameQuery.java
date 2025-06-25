package com.uhexastack.authservice.iam.domain.model.queries;


import com.uhexastack.authservice.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
