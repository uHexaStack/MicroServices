package com.uhexastack.authservice.iam.domain.services;


import com.uhexastack.authservice.iam.domain.model.aggregates.UserAggregate;
import com.uhexastack.authservice.iam.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<UserAggregate> handle(GetAllUsersQuery query);
    Optional<UserAggregate> handle(GetUserByIdQuery query);
    Optional<UserAggregate> handle(GetUserByUsernameQuery query);
}