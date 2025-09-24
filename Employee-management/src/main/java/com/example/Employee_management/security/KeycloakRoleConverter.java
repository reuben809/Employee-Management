package com.example.Employee_management.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Extract realm_access from JWT claims
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return List.of();
        }

        // Extract roles list and convert to Spring Security authorities
        List<String> roles = (List<String>) realmAccess.get("roles");

        if (roles == null || roles.isEmpty()) {
            return List.of();
        }

        return roles.stream()
                .map(roleName -> "ROLE_" + roleName)   // prepend "ROLE_"
                .map(SimpleGrantedAuthority::new)     // wrap as GrantedAuthority
                .collect(Collectors.toList());
    }
}
