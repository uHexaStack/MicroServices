package com.uhexastack.profileservice.profiles.infrastructure.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRegisteredEvent(
        @JsonProperty("userId") Long userId,
        @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName,
        @JsonProperty("ruc") String ruc,
        @JsonProperty("contactEmail") String contactEmail,
        @JsonProperty("contactPhone") String contactPhone,
        @JsonProperty("companyName") String companyName,
        @JsonProperty("companyStreet") String companyStreet,
        @JsonProperty("companyCity") String companyCity,
        @JsonProperty("postalCode") String postalCode,
        @JsonProperty("companyNumber") String companyNumber,
        @JsonProperty("companyCountry") String companyCountry
) {
} 