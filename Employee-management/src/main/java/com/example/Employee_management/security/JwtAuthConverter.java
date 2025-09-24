package com.example.Employee_management.security;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class JwtAuthConverter extends JwtAuthenticationConverter {
    public JwtAuthConverter() {
        this.setJwtGrantedAuthoritiesConverter( new KeycloakRoleConverter());
    }
}
