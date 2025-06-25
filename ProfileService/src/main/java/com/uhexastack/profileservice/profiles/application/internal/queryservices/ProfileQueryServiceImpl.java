package com.uhexastack.profileservice.profiles.application.internal.queryservices;


import com.uhexastack.profileservice.profiles.domain.model.aggregates.ProfileAggregate;
import com.uhexastack.profileservice.profiles.domain.model.queries.*;
import com.uhexastack.profileservice.profiles.domain.services.ProfileQueryService;
import com.uhexastack.profileservice.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the query service for managing user profiles.
 * <p>
 * Provides operations to retrieve profiles by user ID or list all profiles.
 * </p>
 */
@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final ProfileRepository profileRepository;

    /**
     * Constructor that injects the profile repository.
     *
     * @param profileRepository Profile repository.
     */
    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Handles the query to retrieve a profile by user ID.
     *
     * @param query Query containing the user ID.
     * @return An {@link Optional} with the profile if it exists, or empty otherwise.
     */
    @Override
    public Optional<ProfileAggregate> handle(GetProfileByUserIdQuery query) {
        return profileRepository.findByUserId(query.userId());
    }

    /**
     * Handles the query to retrieve all profiles.
     *
     * @param query Query to get all profiles.
     * @return List of all existing profiles.
     */
    @Override
    public List<ProfileAggregate> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}