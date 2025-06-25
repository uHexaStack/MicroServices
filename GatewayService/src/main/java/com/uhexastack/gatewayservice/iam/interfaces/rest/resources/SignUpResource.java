package com.uhexastack.gatewayservice.iam.interfaces.rest.resources;

/**
 * Resource representing the data required for user sign-up.
 * <p>
 * Contains the username, password, and the set of roles assigned to the new user.
 * </p>
 *
 * @param username the username of the user to be registered
 * @param password the password of the user to be registered
 * @param role    the role assigned to the user
 */
public record SignUpResource(
        String username,
        String password,
        String role,
        String firstName,
        String lastName,
        String ruc,
        String contactEmail,
        String contactPhone,
        String companyName,
        String companyStreet,
        String companyCity,
        String postalCode,
        String companyNumber,
        String companyCountry
) {
}