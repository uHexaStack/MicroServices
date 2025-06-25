package com.uhexastack.authservice.iam.domain.services;


import com.uhexastack.authservice.iam.domain.model.aggregates.UserAggregate;
import com.uhexastack.authservice.iam.domain.model.commands.SignInCommand;
import com.uhexastack.authservice.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<UserAggregate> handle(SignUpCommand command);
    Optional<ImmutablePair<UserAggregate, String>> handle(SignInCommand command);
}
