package com.bnaqica.applicantorchestrator.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Component
public class JwtTokenUtils {
    public Map<String, Object> getClaims(Jwt token) {

        return token.getClaims();
    }

    public String getUsername(Jwt token) {
        Map<String, Object> claims = getClaims(token);

        return (String) claims.get("cognito:username");
    }

    public String getEmail(Jwt token) {
        Map<String, Object> claims = getClaims(token);

        return (String) claims.get("email");
    }

    public String getFullName(Jwt token) {
        Map<String, Object> claims = getClaims(token);
        String lastName = (String) claims.get("custom:surname");
        String firstName = (String) claims.get("given_name");

        lastName = isNull(lastName) ? "" : lastName;
        firstName = isNull(firstName) ? "" : firstName;

        return format("%s %s", firstName, lastName);
    }

    public String getFirstName(Jwt token) {
        Map<String, Object> claims = getClaims(token);
        String firstName = (String) claims.get("given_name");

        return isNull(firstName) ? "" : firstName;
    }

    public String getLastName(Jwt token) {
        Map<String, Object> claims = getClaims(token);
        String lastName = (String) claims.get("custom:surname");

        return isNull(lastName) ? "" : lastName;
    }

    public String getPhoneNumber(Jwt token) {
        Map<String, Object> claims = getClaims(token);

        return (String) claims.get("phone_number");
    }

}
