package com.uhexastack.profileservice.profiles.interfaces.rest.resources;

public record ProfileResource(
        Long userId,
        String fullName,
        String ruc,
        String contactEmail,
        String contactPhone,
        String companyInfo
) {
}
