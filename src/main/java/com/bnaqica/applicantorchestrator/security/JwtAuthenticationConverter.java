package com.bnaqica.applicantorchestrator.security;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static Collection<? extends GrantedAuthority> extractCognitoRoles(final Jwt jwt) {
        Optional<JSONArray> cognitoGroups = Optional.ofNullable(jwt.getClaim("cognito:groups"));

        if (cognitoGroups.isPresent()) {
            return cognitoGroups.get().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                    .collect(Collectors.toSet());
        } else {
            return emptySet();
        }
    }

    @Override
    public AbstractAuthenticationToken convert(final Jwt jwt) {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        Collection<GrantedAuthority> authorities = concat(converter.convert(jwt).stream(),
                extractCognitoRoles(jwt).stream()).collect(toSet());

        return new JwtAuthenticationToken(jwt, authorities);
    }

}